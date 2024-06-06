package com.xiao.oasystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.config.rabbitConfig.ApplicationForDepartmentConfig;
import com.xiao.oasystem.config.rabbitConfig.RecordConfig;
import com.xiao.oasystem.exception.ExceedPagingLimitException;
import com.xiao.oasystem.exception.NoSuchApplicationException;
import com.xiao.oasystem.mapper.ApplicationForDepartmentMapper;
import com.xiao.oasystem.pojo.BO.StateUpdateBO;
import com.xiao.oasystem.pojo.DTO.ApplicationForDepartmentDTO;
import com.xiao.oasystem.pojo.VO.UserDepartmentVO;
import com.xiao.oasystem.pojo.entity.ApplicationForDepartment;
import com.xiao.oasystem.pojo.entity.Record;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.ApplicationForDepartmentService;
import com.xiao.oasystem.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.*;

@Service
public class ApplicationForDepartmentServiceImpl implements ApplicationForDepartmentService {

    @Autowired
    private ApplicationForDepartmentMapper applicationForDepartmentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result<ApplicationForDepartment> creatApplication(String inDepartment, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForDepartment application = new ApplicationForDepartment(
                String.valueOf(new Random().nextLong(99999999999L)),
                user.getId(),
                user.getDepartment(),
                inDepartment,
                ApplicationForDepartment.TO_BE_SUBMITTED);
        redisTemplate.opsForSet().add("TOBESUBMIT:" + user.getId(), application.getId());
        rabbitTemplate.convertAndSend(ApplicationForDepartmentConfig.DEPARTMENT_EXCHANGE,
                ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_CREATE_ROUTING,
                new Message(SerializationUtils.serialize(application)));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "创建转部门申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", application);
    }

    @Override
    public Result<Boolean> submitApplication(String id, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForDepartment application = applicationForDepartmentMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        redisTemplate.opsForSet().remove("TOBESUBMIT:" + user.getId(), id);
        redisTemplate.opsForSet().add("DEPARTMENT:" + application.getOutDepartment(), id);
        rabbitTemplate.convertAndSend(ApplicationForDepartmentConfig.DEPARTMENT_EXCHANGE,
                ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_ROUTING,
                new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForDepartment.SUBMITTED))));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "提交转部门申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> deleteApplication(String id, String userId) {
        ApplicationForDepartment application = applicationForDepartmentMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        if(!Objects.equals(userId, application.getInitiator()))
            return Result.fail(Result.SERVER_FAIL, "无法对他人的申请进行此操作", Boolean.FALSE);
        if(ApplicationForDepartment.TO_BE_SUBMITTED != application.getState())
            return Result.fail(Result.SERVER_FAIL, "此申请已被提交，无法删除", Boolean.FALSE);
        redisTemplate.opsForSet().remove("TOBESUBMIT:" + userId, id);
        rabbitTemplate.convertAndSend(ApplicationForDepartmentConfig.DEPARTMENT_EXCHANGE,
                ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_DELETE_ROUTING,
                new Message(SerializationUtils.serialize(id)));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "删除转部门申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForInitiator(String initiator, int pageNum) {
        List<ApplicationForDepartment> applications = applicationForDepartmentMapper.getApplicationsByInitiator(initiator);
        List<ApplicationForDepartmentDTO> result = new ArrayList<>();
        applications.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForDepartmentDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForOutDepartment(String outDepartment, int pageNum) {
        List<ApplicationForDepartment> applications = applicationForDepartmentMapper.getApplicationsByOutDepartment(outDepartment);
        List<ApplicationForDepartmentDTO> result = new ArrayList<>();
        applications.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForDepartmentDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForInDepartment(String inDepartment, int pageNum) {
        List<ApplicationForDepartment> applications = applicationForDepartmentMapper.getApplicationsByInDepartment(inDepartment);
        List<ApplicationForDepartmentDTO> result = new ArrayList<>();
        applications.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForDepartmentDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<ApplicationForDepartment> getApplication(String id) {
        ApplicationForDepartment application = applicationForDepartmentMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        return Result.success(Result.SUCCESS, "操作成功", application);
    }

    @Override
    public Result<Boolean> agreeApplication(String id, String userId) {
        ApplicationForDepartment application = applicationForDepartmentMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        User user = userService.getUser(userId).getData();
        if(Objects.equals(application.getOutDepartment(), user.getDepartment())) {
            redisTemplate.opsForSet().remove("DEPARTMENT:" + user.getDepartment(), application.getId());
            redisTemplate.opsForSet().add("DEPARTMENT:" + application.getInDepartment(), application.getId());
            rabbitTemplate.convertAndSend(ApplicationForDepartmentConfig.DEPARTMENT_EXCHANGE,
                    ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_ROUTING,
                    new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForDepartment.PASS_FOR_OUT))));
            rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                    RecordConfig.RECORD_ROUTING,
                    new Message(SerializationUtils.serialize(new Record(userId, "同意转出部门:" + application.getId(), new Date()))));
        }
        if(Objects.equals(application.getInDepartment(), user.getDepartment())) {
            redisTemplate.opsForSet().remove("DEPARTMENT:" + user.getDepartment(), application.getId());
            rabbitTemplate.convertAndSend(ApplicationForDepartmentConfig.DEPARTMENT_EXCHANGE,
                    ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_ROUTING,
                    new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForDepartment.PASS_FOR_IN))));
            rabbitTemplate.convertAndSend(ApplicationForDepartmentConfig.DEPARTMENT_EXCHANGE,
                    ApplicationForDepartmentConfig.DEPARTMENT_CHANGE_ROUTING,
                    new Message(SerializationUtils.serialize(new UserDepartmentVO(application.getInitiator(), id))));
            rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                    RecordConfig.RECORD_ROUTING,
                    new Message(SerializationUtils.serialize(new Record(userId, "同意转入部门:" + application.getId(), new Date()))));
        }
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> refuseApplication(String id, String userId) {
        ApplicationForDepartment application = applicationForDepartmentMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        User user = userService.getUser(userId).getData();
        redisTemplate.opsForSet().remove("DEPARTMENT:" + user.getDepartment(), application.getId());
        rabbitTemplate.convertAndSend(ApplicationForDepartmentConfig.DEPARTMENT_EXCHANGE,
                ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_ROUTING,
                new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForDepartment.REFUSED))));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "拒绝转部门:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<List<ApplicationForDepartmentDTO>> list(int pageNum) {
        List<ApplicationForDepartmentDTO> result = new ArrayList<>();
        List<ApplicationForDepartment> applications = applicationForDepartmentMapper.list();
        if(0 == pageNum)
            applications.stream()
                    .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForDepartmentDTO.class)));
        else{
            applications.stream()
                    .skip(5 * (pageNum - 1))
                    .limit(5)
                    .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForDepartmentDTO.class)));
            if(result.isEmpty())
                throw new ExceedPagingLimitException("超出分页上限");
        }
        return Result.success(Result.SUCCESS, "操作成功", result);
    }
}

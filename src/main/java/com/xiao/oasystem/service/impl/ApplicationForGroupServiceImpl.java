package com.xiao.oasystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.xiao.oasystem.config.rabbitConfig.ApplicationForGroupConfig;
import com.xiao.oasystem.config.rabbitConfig.RecordConfig;
import com.xiao.oasystem.exception.ExceedPagingLimitException;
import com.xiao.oasystem.exception.NoSuchApplicationException;
import com.xiao.oasystem.mapper.ApplicationForGroupMapper;
import com.xiao.oasystem.pojo.BO.StateUpdateBO;
import com.xiao.oasystem.pojo.DTO.ApplicationForGroupDTO;
import com.xiao.oasystem.pojo.VO.UserGroupVO;
import com.xiao.oasystem.pojo.entity.ApplicationForGroup;
import com.xiao.oasystem.pojo.entity.Record;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.ApplicationForGroupService;
import com.xiao.oasystem.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.*;

@Service
public class ApplicationForGroupServiceImpl implements ApplicationForGroupService {

    @Autowired
    private ApplicationForGroupMapper applicationForGroupMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result<ApplicationForGroup> creatApplication(String inGroup, String userId) {
        User user = userService.getUser(userId).getData();
        if(user.getDepartment().isEmpty())
            return Result.fail(Result.SERVER_FAIL, "该用户未分部门", null);
        ApplicationForGroup application = new ApplicationForGroup(
                String.valueOf(new Random().nextLong(99999999999L)),
                user.getId(),
                user.getGroup(),
                inGroup,
                ApplicationForGroup.TO_BE_SUBMITTED);
        redisTemplate.opsForSet().add("TOBESUBMIT:" + user.getId(), application.getId());
        rabbitTemplate.convertAndSend(ApplicationForGroupConfig.GROUP_EXCHANGE,
                ApplicationForGroupConfig.APPLICATION_FOR_GROUP_CREATE_ROUTING,
                new Message(SerializationUtils.serialize(application)));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "创建转小组申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", application);
    }

    @Override
    public Result<Boolean> submitApplication(String id, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForGroup application = applicationForGroupMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        redisTemplate.opsForSet().remove("TOBESUBMIT:" + user.getId(), id);
        redisTemplate.opsForSet().add("GROUP:" + application.getOutGroup(), id);
        rabbitTemplate.convertAndSend(ApplicationForGroupConfig.GROUP_EXCHANGE,
                ApplicationForGroupConfig.APPLICATION_FOR_GROUP_STATE_CHANGE_ROUTING,
                new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForGroup.SUBMITTED))));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "提交转小组申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> deleteApplication(String id, String userId) {
        ApplicationForGroup application = applicationForGroupMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        if(!Objects.equals(userId, application.getInitiator()))
            return Result.fail(Result.SERVER_FAIL, "无法对他人的申请进行此操作", Boolean.FALSE);
        if(ApplicationForGroup.TO_BE_SUBMITTED != application.getState())
            return Result.fail(Result.SERVER_FAIL, "此申请已被提交，无法删除", Boolean.FALSE);
        redisTemplate.opsForSet().remove("TOBESUBMIT:" + userId, id);
        rabbitTemplate.convertAndSend(ApplicationForGroupConfig.GROUP_EXCHANGE,
                ApplicationForGroupConfig.APPLICATION_FOR_GROUP_DELETE_ROUTING,
                new Message(SerializationUtils.serialize(id)));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "删除转小组申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<List<ApplicationForGroupDTO>> getApplicationsForInitiator(String initiator, int pageNum) {
        List<ApplicationForGroup> applications = applicationForGroupMapper.getApplicationsByInitiator(initiator);
        List<ApplicationForGroupDTO> result = new ArrayList<>();
        applications.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForGroupDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<List<ApplicationForGroupDTO>> getApplicationsForOutGroup(String outGroup, int pageNum) {
        List<ApplicationForGroup> applications = applicationForGroupMapper.getApplicationsByOutGroup(outGroup);
        List<ApplicationForGroupDTO> result = new ArrayList<>();
        applications.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForGroupDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<List<ApplicationForGroupDTO>> getApplicationsForInGroup(String inGroup, int pageNum) {
        List<ApplicationForGroup> applications = applicationForGroupMapper.getApplicationsByInGroup(inGroup);
        List<ApplicationForGroupDTO> result = new ArrayList<>();
        applications.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForGroupDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<ApplicationForGroup> getApplication(String id) {
        ApplicationForGroup application = applicationForGroupMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        return Result.success(Result.SUCCESS, "操作成功", application);
    }

    @Override
    public Result<Boolean> agreeApplication(String id, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForGroup application = applicationForGroupMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        if(Objects.equals(user.getGroup(), application.getOutGroup())) {
            redisTemplate.opsForSet().remove("GROUP:" + user.getGroup(), application.getId());
            redisTemplate.opsForSet().add("GROUP:" + application.getInGroup(), application.getId());
            rabbitTemplate.convertAndSend(ApplicationForGroupConfig.GROUP_EXCHANGE,
                    ApplicationForGroupConfig.APPLICATION_FOR_GROUP_STATE_CHANGE_ROUTING,
                    new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForGroup.PASS_FOR_OUT))));
            rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                    RecordConfig.RECORD_ROUTING,
                    new Message(SerializationUtils.serialize(new Record(userId, "同意转出小组:" + application.getId(), new Date()))));
        }
        if(Objects.equals(user.getGroup(), application.getInGroup())){
            redisTemplate.opsForSet().remove("GROUP:" + user.getGroup(), application.getId());
            rabbitTemplate.convertAndSend(ApplicationForGroupConfig.GROUP_EXCHANGE,
                    ApplicationForGroupConfig.APPLICATION_FOR_GROUP_STATE_CHANGE_ROUTING,
                    new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForGroup.PASS_FOR_IN))));
            rabbitTemplate.convertAndSend(ApplicationForGroupConfig.GROUP_EXCHANGE,
                    ApplicationForGroupConfig.GROUP_CHANGE_ROUTING,
                    new Message(SerializationUtils.serialize(new UserGroupVO(application.getInitiator(), id))));
            rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                    RecordConfig.RECORD_ROUTING,
                    new Message(SerializationUtils.serialize(new Record(userId, "同意转入小组:" + application.getId(), new Date()))));
        }
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> refuseApplication(String id, String userId) {
        ApplicationForGroup application = applicationForGroupMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        User user = userService.getUser(userId).getData();
        redisTemplate.opsForSet().remove("GROUP:" + user.getGroup(), application.getId());
        rabbitTemplate.convertAndSend(ApplicationForGroupConfig.GROUP_EXCHANGE,
                ApplicationForGroupConfig.APPLICATION_FOR_GROUP_STATE_CHANGE_ROUTING,
                new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForGroup.REFUSED))));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "拒绝转小组:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<List<ApplicationForGroupDTO>> list(int pageNum) {
        List<ApplicationForGroupDTO> result = new ArrayList<>();
        List<ApplicationForGroup> applications = applicationForGroupMapper.list();
        if(0 == pageNum)
            applications.stream()
                    .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForGroupDTO.class)));
        else{
            applications.stream()
                    .skip(5 * (pageNum - 1))
                    .limit(5)
                    .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForGroupDTO.class)));
            if(result.isEmpty())
                throw new ExceedPagingLimitException("超出分页上限");
        }
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

}

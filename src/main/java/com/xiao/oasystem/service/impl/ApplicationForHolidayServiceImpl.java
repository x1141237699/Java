package com.xiao.oasystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.xiao.oasystem.config.rabbitConfig.ApplicationForHolidayConfig;
import com.xiao.oasystem.config.rabbitConfig.RecordConfig;
import com.xiao.oasystem.exception.ExceedPagingLimitException;
import com.xiao.oasystem.exception.NoSuchApplicationException;
import com.xiao.oasystem.mapper.ApplicationForHolidayMapper;
import com.xiao.oasystem.pojo.BO.StateUpdateBO;
import com.xiao.oasystem.pojo.DTO.ApplicationForHolidayDTO;
import com.xiao.oasystem.pojo.entity.ApplicationForGroup;
import com.xiao.oasystem.pojo.entity.ApplicationForHoliday;
import com.xiao.oasystem.pojo.entity.Record;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.ApplicationForHolidayService;
import com.xiao.oasystem.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.*;

@Service
public class ApplicationForHolidayServiceImpl implements ApplicationForHolidayService {

    @Autowired
    private ApplicationForHolidayMapper applicationForHolidayMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result<ApplicationForHoliday> creatApplication(String reason, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForHoliday application;
        if(user.getPosition() == User.NORMAL_USER) {
            application = new ApplicationForHoliday(String.valueOf(new Random().nextLong(99999999999L)),
                    user.getId(),
                    user.getGroup(),
                    reason,
                    ApplicationForHoliday.TO_BE_SUBMITTED,
                    ApplicationForHoliday.GROUP);
            redisTemplate.opsForSet().add("TOBESUBMIT:" + user.getId(), application.getId());
            rabbitTemplate.convertAndSend(ApplicationForHolidayConfig.HOLIDAY_EXCHANGE,
                    ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_CREATE_ROUTING,
                    new Message(SerializationUtils.serialize(application)));
        }else {
            application = new ApplicationForHoliday(String.valueOf(new Random().nextLong(99999999999L)),
                    user.getId(),
                    user.getDepartment(),
                    reason,
                    ApplicationForHoliday.TO_BE_SUBMITTED,
                    ApplicationForHoliday.DEPARTMENT);
            redisTemplate.opsForSet().add("TOBESUBMIT:" + user.getId(), application.getId());
            rabbitTemplate.convertAndSend(ApplicationForHolidayConfig.HOLIDAY_EXCHANGE,
                    ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_CREATE_ROUTING,
                    new Message(SerializationUtils.serialize(application)));
        }
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "创建请假申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", application);
    }

    @Override
    public Result<Boolean> submitApplication(String id, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForHoliday application = applicationForHolidayMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        redisTemplate.opsForSet().remove("TOBESUBMIT:" + user.getId(), id);
        switch (user.getPosition()){
            case User.NORMAL_USER -> redisTemplate.opsForSet().add("HOLIDAY:GROUP:" + application.getRecipient(), id);
            case User.GROUP_MANAGER -> redisTemplate.opsForSet().add("HOLIDAY:DEPARTMENT:" + application.getRecipient(), id);
        }
        rabbitTemplate.convertAndSend(ApplicationForHolidayConfig.HOLIDAY_EXCHANGE,
                ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_STATE_CHANGE_ROUTING,
                new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForHoliday.SUBMITTED))));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "提交请假申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> deleteApplication(String id, String userId) {
        ApplicationForHoliday application = applicationForHolidayMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        if(!Objects.equals(userId, application.getInitiator()))
            return Result.fail(Result.SERVER_FAIL, "无法对他人的申请进行此操作", Boolean.FALSE);
        if(ApplicationForGroup.TO_BE_SUBMITTED != application.getState())
            return Result.fail(Result.SERVER_FAIL, "此申请已被提交，无法删除", Boolean.FALSE);
        redisTemplate.opsForSet().remove("TOBESUBMIT:" + userId, id);
        rabbitTemplate.convertAndSend(ApplicationForHolidayConfig.HOLIDAY_EXCHANGE,
                ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_DELETE_ROUTING,
                new Message(SerializationUtils.serialize(id)));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "删除请假申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<List<ApplicationForHolidayDTO>> getApplicationsForInitiator(String initiator, int pageNum) {
        List<ApplicationForHoliday> applications = applicationForHolidayMapper.getApplicationsByInitiator(initiator);
        List<ApplicationForHolidayDTO> result = new ArrayList<>();
        applications.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(applicationForHoliday -> result.add(BeanUtil.toBean(applicationForHoliday, ApplicationForHolidayDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<List<ApplicationForHolidayDTO>> getApplicationsForRecipient(String recipient, int pageNum) {
        List<ApplicationForHoliday> applications = applicationForHolidayMapper.getApplicationsByRecipient(recipient);
        List<ApplicationForHolidayDTO> result = new ArrayList<>();
        applications.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(applicationForHoliday -> result.add(BeanUtil.toBean(applicationForHoliday, ApplicationForHolidayDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<ApplicationForHoliday> getApplication(String id) {
        ApplicationForHoliday application = applicationForHolidayMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        return Result.success(Result.SUCCESS, "操作成功", application);
    }

    @Override
    public Result<Boolean> agreeApplication(String id, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForHoliday application = applicationForHolidayMapper.getApplicationById(id);
        if(null == application)
            throw new NoSuchApplicationException(id);
        switch (user.getPosition()){
            case User.GROUP_MANAGER -> redisTemplate.opsForSet().remove("HOLIDAY:GROUP:" + application.getRecipient(), id);
            case User.DEPARTMENT_MANAGER -> redisTemplate.opsForSet().remove("HOLIDAY:DEPARTMENT" + application.getRecipient(), id);
        }
        rabbitTemplate.convertAndSend(ApplicationForHolidayConfig.HOLIDAY_EXCHANGE,
                ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_STATE_CHANGE_ROUTING,
                new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForHoliday.PASSED))));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "同意请假申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> refuseApplication(String id, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForHoliday application = applicationForHolidayMapper.getApplicationById(id);
        if(null == applicationForHolidayMapper.getApplicationById(id))
            throw new NoSuchApplicationException(id);
        switch (user.getPosition()){
            case User.GROUP_MANAGER -> redisTemplate.opsForSet().remove("HOLIDAY:GROUP:" + application.getRecipient(), id);
            case User.DEPARTMENT_MANAGER -> redisTemplate.opsForSet().remove("HOLIDAY:DEPARTMENT" + application.getRecipient(), id);
        }
        rabbitTemplate.convertAndSend(ApplicationForHolidayConfig.HOLIDAY_EXCHANGE,
                ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_STATE_CHANGE_ROUTING,
                new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForHoliday.REFUSED))));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "拒绝请假申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> rejectApplication(String id, String userId) {
        User user = userService.getUser(userId).getData();
        ApplicationForHoliday application = applicationForHolidayMapper.getApplicationById(id);
        if(null == applicationForHolidayMapper.getApplicationById(id))
            throw new NoSuchApplicationException(id);
        switch (user.getPosition()){
            case User.GROUP_MANAGER -> redisTemplate.opsForSet().remove("HOLIDAY:GROUP:" + application.getRecipient(), id);
            case User.DEPARTMENT_MANAGER -> redisTemplate.opsForSet().remove("HOLIDAY:DEPARTMENT" + application.getRecipient(), id);
        }
        redisTemplate.opsForSet().add("TOBESUBMIT:" + application.getInitiator(), application.getId());
        rabbitTemplate.convertAndSend(ApplicationForHolidayConfig.HOLIDAY_EXCHANGE,
                ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_STATE_CHANGE_ROUTING,
                new Message(SerializationUtils.serialize(new StateUpdateBO(id, ApplicationForHoliday.TO_BE_SUBMITTED))));
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "驳回请假申请:" + application.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<List<ApplicationForHolidayDTO>> list(int pageNum) {
        List<ApplicationForHolidayDTO> result = new ArrayList<>();
        List<ApplicationForHoliday> applications = applicationForHolidayMapper.list();
        if(0 == pageNum)
            applications.stream()
                    .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForHolidayDTO.class)));
        else{
            applications.stream()
                    .skip(5 * (pageNum - 1))
                    .limit(5)
                    .forEach(application -> result.add(BeanUtil.toBean(application, ApplicationForHolidayDTO.class)));
            if(result.isEmpty())
                throw new ExceedPagingLimitException("超出分页上限");
        }
        return Result.success(Result.SUCCESS, "操作成功", result);
    }
}

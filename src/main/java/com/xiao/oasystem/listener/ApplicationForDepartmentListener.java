package com.xiao.oasystem.listener;

import cn.hutool.core.bean.BeanUtil;
import com.rabbitmq.client.Channel;
import com.xiao.oasystem.config.rabbitConfig.ApplicationForDepartmentConfig;
import com.xiao.oasystem.mapper.ApplicationForDepartmentMapper;
import com.xiao.oasystem.pojo.BO.StateUpdateBO;
import com.xiao.oasystem.pojo.VO.UserDepartmentVO;
import com.xiao.oasystem.pojo.entity.ApplicationForDepartment;
import com.xiao.oasystem.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationForDepartmentListener {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationForDepartmentMapper applicationForDepartmentMapper;

    @RabbitListener(queues = ApplicationForDepartmentConfig.DEPARTMENT_CHANGE_QUEUE)
    public void receiveDepartmentChange(Object msg, Message message, Channel channel){
        UserDepartmentVO userDepartmentVO = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()), UserDepartmentVO.class);
        userService.updateUserDepartment(userDepartmentVO);
    }

    @RabbitListener(queues = ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_CREATE_QUEUE)
    public void receiveApplicationCreate(Object msg, Message message, Channel channel){
        ApplicationForDepartment application = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()), ApplicationForDepartment.class);
        applicationForDepartmentMapper.insertApplication(application);
    }

    @RabbitListener(queues = ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_QUEUE)
    public void receiveApplicationStateChange(Object msg, Message message, Channel channel){
        StateUpdateBO stateUpdateBO = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()), StateUpdateBO.class);
        applicationForDepartmentMapper.updateApplicationState(stateUpdateBO.getId(), stateUpdateBO.getState());
    }

    @RabbitListener(queues = ApplicationForDepartmentConfig.APPLICATION_FOR_DEPARTMENT_DELETE_QUEUE)
    public void receiveApplicationDelete(Object msg, Message message, Channel channel){
        String id = SerializationUtils.deserialize(message.getBody()).toString();
        applicationForDepartmentMapper.deleteApplication(id);
    }
}

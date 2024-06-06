package com.xiao.oasystem.listener;

import cn.hutool.core.bean.BeanUtil;
import com.rabbitmq.client.Channel;
import com.xiao.oasystem.config.rabbitConfig.ApplicationForGroupConfig;
import com.xiao.oasystem.mapper.ApplicationForGroupMapper;
import com.xiao.oasystem.pojo.BO.StateUpdateBO;
import com.xiao.oasystem.pojo.VO.UserGroupVO;
import com.xiao.oasystem.pojo.entity.ApplicationForGroup;
import com.xiao.oasystem.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationForGroupListener {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationForGroupMapper applicationForGroupMapper;

    @RabbitListener(queues = ApplicationForGroupConfig.GROUP_CHANGE_QUEUE)
    public void receiveGroupChange(Object msg, Message message, Channel channel){
        UserGroupVO userGroupVO = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()), UserGroupVO.class);
        userService.updateUserGroup(userGroupVO);
    }

    @RabbitListener(queues = ApplicationForGroupConfig.APPLICATION_FOR_GROUP_CREATE_QUEUE)
    public void receiveApplicationCreate(Object msg, Message message, Channel channel){
        ApplicationForGroup application = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()), ApplicationForGroup.class);
        applicationForGroupMapper.insertApplication(application);
    }

    @RabbitListener(queues = ApplicationForGroupConfig.APPLICATION_FOR_GROUP_STATE_CHANGE_QUEUE)
    public void receiveApplicationStateChange(Object msg, Message message, Channel channel){
        StateUpdateBO stateUpdateBO = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()), StateUpdateBO.class);
        applicationForGroupMapper.updateApplicationState(stateUpdateBO.getId(), stateUpdateBO.getState());
    }

    @RabbitListener(queues = ApplicationForGroupConfig.APPLICATION_FOR_GROUP_DELETE_QUEUE)
    public void receiveApplicationDelete(Object msg, Message message, Channel channel){
        String id = SerializationUtils.deserialize(message.getBody()).toString();
        applicationForGroupMapper.deleteApplication(id);
    }
}

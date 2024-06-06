package com.xiao.oasystem.listener;

import cn.hutool.core.bean.BeanUtil;
import com.rabbitmq.client.Channel;
import com.xiao.oasystem.config.rabbitConfig.ApplicationForHolidayConfig;
import com.xiao.oasystem.mapper.ApplicationForHolidayMapper;
import com.xiao.oasystem.pojo.BO.StateUpdateBO;
import com.xiao.oasystem.pojo.entity.ApplicationForHoliday;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationForHolidayListener {

    @Autowired
    private ApplicationForHolidayMapper applicationForHolidayMapper;

    @RabbitListener(queues = ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_CREATE_QUEUE)
    public void receiveApplicationCreate(Object msg, Message message, Channel channel){
        ApplicationForHoliday application = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()), ApplicationForHoliday.class);
        applicationForHolidayMapper.insertApplication(application);
    }

    @RabbitListener(queues = ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_STATE_CHANGE_QUEUE)
    public void receiveApplicationStateChange(Object msg, Message message, Channel channel){
        StateUpdateBO stateUpdateBO = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()), StateUpdateBO.class);
        applicationForHolidayMapper.updateApplicationState(stateUpdateBO.getId(), stateUpdateBO.getState());
    }

    @RabbitListener(queues = ApplicationForHolidayConfig.APPLICATION_FOR_HOLIDAY_DELETE_QUEUE)
    public void receiveApplicationDelete(Object msg, Message message, Channel channel){
        String id = SerializationUtils.deserialize(message.getBody()).toString();
        applicationForHolidayMapper.deleteApplication(id);
    }
}

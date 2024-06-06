package com.xiao.oasystem.listener;

import cn.hutool.core.bean.BeanUtil;
import com.rabbitmq.client.Channel;
import com.xiao.oasystem.config.rabbitConfig.RecordConfig;
import com.xiao.oasystem.mapper.RecordMapper;
import com.xiao.oasystem.pojo.entity.Record;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordListener {

    @Autowired
    private RecordMapper recordMapper;

    @RabbitListener(queues = RecordConfig.RECORD_QUEUE)
    public void receiveApplicationCreate(Object msg, Message message, Channel channel){
        Record record = BeanUtil.toBean(SerializationUtils.deserialize(message.getBody()),Record.class);
        recordMapper.insertRecord(record);
    }
}

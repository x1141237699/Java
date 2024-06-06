package com.xiao.oasystem.config.rabbitConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecordConfig {
    //交换机
    public static final String RECORD_EXCHANGE = "RecordExchange";

    //队列
    public static final String RECORD_QUEUE = "RecordQueue";

    //key
    public static final String RECORD_ROUTING = "RecordRouting";

    @Bean
    public Queue RecordQueue() {
        return new Queue(RECORD_QUEUE);
    }

    @Bean
    public DirectExchange Exchange() {
        return new DirectExchange(RECORD_EXCHANGE);
    }

    @Bean
    public Binding RecordBinding() {
        return BindingBuilder.bind(RecordQueue()).to(Exchange()).with(RECORD_ROUTING);
    }
}

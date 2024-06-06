package com.xiao.oasystem.config.rabbitConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationForGroupConfig {

    //交换机
    public static final String GROUP_EXCHANGE = "GroupExchange";

    //队列
    public static final String GROUP_CHANGE_QUEUE = "GroupChangeQueue";
    public static final String APPLICATION_FOR_GROUP_CREATE_QUEUE = "ApplicationForGroupCreateQueue";
    public static final String APPLICATION_FOR_GROUP_STATE_CHANGE_QUEUE = "ApplicationForGroupStateChangeQueue";
    public static final String APPLICATION_FOR_GROUP_DELETE_QUEUE = "ApplicationForGroupDeleteQueue";

    //key
    public static final String GROUP_CHANGE_ROUTING = "GroupDirectRouting";
    public static final String APPLICATION_FOR_GROUP_CREATE_ROUTING = "ApplicationForGroupCreateRouting";
    public static final String APPLICATION_FOR_GROUP_STATE_CHANGE_ROUTING = "ApplicationForGroupStateChangeRouting";
    public static final String APPLICATION_FOR_GROUP_DELETE_ROUTING = "ApplicationForGroupDeleteRouting";

    @Bean
    public Queue GroupQueue() {
        return new Queue(GROUP_CHANGE_QUEUE);
    }

    @Bean
    public Queue ApplicationForGroupCreateQueue() {
        return new Queue(APPLICATION_FOR_GROUP_CREATE_QUEUE);
    }

    @Bean
    public Queue ApplicationForGroupStateChangeQueue() {
        return new Queue(APPLICATION_FOR_GROUP_STATE_CHANGE_QUEUE);
    }

    @Bean
    public Queue ApplicationForGroupDeleteQueue() {
        return new Queue(APPLICATION_FOR_GROUP_DELETE_QUEUE);
    }

    @Bean
    public DirectExchange ApplicationForGroupExchange() {
        return new DirectExchange(GROUP_EXCHANGE);
    }

    @Bean
    public Binding GroupBinding() {
        return BindingBuilder.bind(GroupQueue()).to(ApplicationForGroupExchange()).with(GROUP_CHANGE_ROUTING);
    }

    @Bean
    public Binding ApplicationForGroupCreateBinding() {
        return BindingBuilder.bind(ApplicationForGroupCreateQueue()).to(ApplicationForGroupExchange()).with(APPLICATION_FOR_GROUP_CREATE_ROUTING);
    }

    @Bean
    public Binding ApplicationForGroupStateChangeBinding() {
        return BindingBuilder.bind(ApplicationForGroupStateChangeQueue()).to(ApplicationForGroupExchange()).with(APPLICATION_FOR_GROUP_STATE_CHANGE_ROUTING);
    }

    @Bean
    public Binding ApplicationForGroupDeleteBinding() {
        return BindingBuilder.bind(ApplicationForGroupDeleteQueue()).to(ApplicationForGroupExchange()).with(APPLICATION_FOR_GROUP_DELETE_ROUTING);
    }
}

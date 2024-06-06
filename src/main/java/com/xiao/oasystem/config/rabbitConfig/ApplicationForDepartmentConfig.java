package com.xiao.oasystem.config.rabbitConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationForDepartmentConfig {

    //交换机
    public static final String DEPARTMENT_EXCHANGE = "DepartmentExchange";

    //队列
    public static final String DEPARTMENT_CHANGE_QUEUE = "DepartmentChangeQueue";
    public static final String APPLICATION_FOR_DEPARTMENT_CREATE_QUEUE = "ApplicationForDepartmentCreateQueue";
    public static final String APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_QUEUE = "ApplicationForDepartmentStateChangeQueue";
    public static final String APPLICATION_FOR_DEPARTMENT_DELETE_QUEUE = "ApplicationForDepartmentDeleteQueue";

    //key
    public static final String DEPARTMENT_CHANGE_ROUTING = "DepartmentDirectRouting";
    public static final String APPLICATION_FOR_DEPARTMENT_CREATE_ROUTING = "ApplicationForDepartmentCreateRouting";
    public static final String APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_ROUTING = "ApplicationForDepartmentStateChangeRouting";
    public static final String APPLICATION_FOR_DEPARTMENT_DELETE_ROUTING = "ApplicationForDepartmentDeleteRouting";

    @Bean
    public Queue DepartmentChangeQueue() {
        return new Queue(DEPARTMENT_CHANGE_QUEUE);
    }

    @Bean
    public Queue ApplicationForDepartmentCreateQueue(){
        return new Queue(APPLICATION_FOR_DEPARTMENT_CREATE_QUEUE);
    }

    @Bean
    public Queue ApplicationForDepartmentStateChangeQueue(){
        return new Queue(APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_QUEUE);
    }

    @Bean
    public Queue ApplicationForDepartmentDeleteQueue() {
        return new Queue(APPLICATION_FOR_DEPARTMENT_DELETE_QUEUE);
    }

    @Bean
    public DirectExchange ApplicationForDepartmentExchange() {
        return new DirectExchange(DEPARTMENT_EXCHANGE);
    }

    @Bean
    public Binding DepartmentChangeBinding() {
        return BindingBuilder.bind(DepartmentChangeQueue()).to(ApplicationForDepartmentExchange()).with(DEPARTMENT_CHANGE_ROUTING);
    }

    @Bean
    public Binding ApplicationForDepartmentCreateBinding(){
        return BindingBuilder.bind(ApplicationForDepartmentCreateQueue()).to(ApplicationForDepartmentExchange()).with(APPLICATION_FOR_DEPARTMENT_CREATE_ROUTING);
    }

    @Bean
    public Binding ApplicationForDepartmentStateChangeBinding(){
        return BindingBuilder.bind(ApplicationForDepartmentStateChangeQueue()).to(ApplicationForDepartmentExchange()).with(APPLICATION_FOR_DEPARTMENT_STATE_CHANGE_ROUTING);
    }

    @Bean
    public Binding ApplicationForDepartmentDeleteBinding() {
        return BindingBuilder.bind(ApplicationForDepartmentDeleteQueue()).to(ApplicationForDepartmentExchange()).with(APPLICATION_FOR_DEPARTMENT_DELETE_ROUTING);
    }
}

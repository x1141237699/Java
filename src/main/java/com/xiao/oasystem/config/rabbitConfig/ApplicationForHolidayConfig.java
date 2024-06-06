package com.xiao.oasystem.config.rabbitConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationForHolidayConfig {
    //交换机
    public static final String HOLIDAY_EXCHANGE = "HolidayExchange";

    //队列
    public static final String APPLICATION_FOR_HOLIDAY_CREATE_QUEUE = "ApplicationForHolidayCreateQueue";
    public static final String APPLICATION_FOR_HOLIDAY_STATE_CHANGE_QUEUE = "ApplicationForHolidayStateChangeQueue";
    public static final String APPLICATION_FOR_HOLIDAY_DELETE_QUEUE = "ApplicationForHolidayDeleteQueue";

    //key
    public static final String APPLICATION_FOR_HOLIDAY_CREATE_ROUTING = "ApplicationForHolidayCreateRouting";
    public static final String APPLICATION_FOR_HOLIDAY_STATE_CHANGE_ROUTING = "ApplicationForHolidayStateChangeRouting";
    public static final String APPLICATION_FOR_HOLIDAY_DELETE_ROUTING = "ApplicationForHolidayDeleteRouting";

    @Bean
    public Queue ApplicationForHolidayCreateQueue() {
        return new Queue(APPLICATION_FOR_HOLIDAY_CREATE_QUEUE);
    }

    @Bean
    public Queue ApplicationForHolidayStateChangeQueue() {
        return new Queue(APPLICATION_FOR_HOLIDAY_STATE_CHANGE_QUEUE);
    }

    @Bean
    public Queue ApplicationForHolidayDeleteQueue() {
        return new Queue(APPLICATION_FOR_HOLIDAY_DELETE_QUEUE);
    }

    @Bean
    public DirectExchange ApplicationForHolidayExchange() {
        return new DirectExchange(HOLIDAY_EXCHANGE);
    }

    @Bean
    public Binding ApplicationForHolidayCreateBinding() {
        return BindingBuilder.bind(ApplicationForHolidayCreateQueue()).to(ApplicationForHolidayExchange()).with(APPLICATION_FOR_HOLIDAY_CREATE_ROUTING);
    }

    @Bean
    public Binding ApplicationForHolidayStateChangeBinding() {
        return BindingBuilder.bind(ApplicationForHolidayStateChangeQueue()).to(ApplicationForHolidayExchange()).with(APPLICATION_FOR_HOLIDAY_STATE_CHANGE_ROUTING);
    }

    @Bean
    public Binding ApplicationForHolidayDeleteBinding() {
        return BindingBuilder.bind(ApplicationForHolidayDeleteQueue()).to(ApplicationForHolidayExchange()).with(APPLICATION_FOR_HOLIDAY_DELETE_ROUTING);
    }
}

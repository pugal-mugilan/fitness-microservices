package com.fitness.activityservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue activityQueue() {
        return new Queue("activity.queue", true);
    }


}

package com.homates.userhouses.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DelHouseService {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public DelHouseService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void sendMessage(String item) {
        rabbitTemplate.convertAndSend(exchange, routingkey, item);
    }
}

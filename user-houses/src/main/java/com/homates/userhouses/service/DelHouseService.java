package com.homates.userhouses.service;

import com.homates.userhouses.dto.DelHouseMessage;
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

    @Value("${spring.rabbitmq.routingkeyboard}")
    private String routingkeyboard;

    @Value("${spring.rabbitmq.routingkeywallet}")
    private String routingkeywallet;

    @Value("${spring.rabbitmq.routingkeyshopl}")
    private String routingkeyshopl;

    public void sendMessage(DelHouseMessage item) {
        rabbitTemplate.convertAndSend(exchange, routingkeyboard, item);
        rabbitTemplate.convertAndSend(exchange, routingkeywallet, item);
        rabbitTemplate.convertAndSend(exchange, routingkeyshopl, item);
    }
}

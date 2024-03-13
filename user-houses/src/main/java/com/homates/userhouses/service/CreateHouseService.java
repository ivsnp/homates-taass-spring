package com.homates.userhouses.service;

import com.homates.userhouses.dto.HouseMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateHouseService {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public CreateHouseService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkeycalendar}")
    private String routingkeycalendar;

    public void sendMessage(HouseMessage item) {
        rabbitTemplate.convertAndSend(exchange, routingkeycalendar, item);
    }
}

package com.homates.bacheca.service;

import com.homates.bacheca.dto.HouseMessage;
import com.homates.bacheca.model.Announce;
import com.homates.bacheca.repo.AnnounceRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumerService {
    @Autowired
    AnnounceRepository repository;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(HouseMessage item) {
        switch(item.getMessageType()) {
            case DEL:
                System.out.println("Get all announces related to house "+item.getIdHouse());
                List<Announce> announceList = repository.findByIdHouse(item.getIdHouse());
                for (Announce a : announceList) {
                    repository.deleteById(a.getId());
                }
                break;
            default:
                System.out.println("MessageType not found");
        }
    }
}

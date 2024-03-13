package com.homates.shoppinglist.service;

import com.homates.shoppinglist.dto.DelHouseMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    /*private final UserRepository userRepository;

    @Autowired
    public ConsumerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(DelHouseMessage item) {
        /*User save = userRepository.save(user);
        logger.info("persisted " + save);
        logger.info("User recieved: " + user);*/
        System.out.println("SHOPL: "+item.getMessage());
    }
}

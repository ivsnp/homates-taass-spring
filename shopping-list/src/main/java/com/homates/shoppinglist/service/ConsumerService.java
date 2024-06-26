package com.homates.shoppinglist.service;

import com.homates.shoppinglist.dto.HouseMessage;
import com.homates.shoppinglist.model.ShoppingList;
import com.homates.shoppinglist.repo.ProductInListRepository;
import com.homates.shoppinglist.repo.ShoppingListRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumerService {
    @Autowired
    ShoppingListRepository shoppingListRepository;

    @Autowired
    ProductInListRepository productInListRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(HouseMessage item) {
        switch(item.getMessageType()) {
            case DEL:
                System.out.println("Deleting shopping lists related to house "+item.getIdHouse());
                List<ShoppingList> shoppingList = shoppingListRepository.findByIdHouse(item.getIdHouse());
                for (ShoppingList s : shoppingList) {
                    shoppingListRepository.deleteById(s.getId());
                }
                break;
            default:
                System.out.println("MessageType not found");
        }
    }
}

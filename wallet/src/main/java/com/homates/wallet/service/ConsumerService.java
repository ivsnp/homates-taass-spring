package com.homates.wallet.service;

import com.homates.wallet.dto.HouseMessage;
import com.homates.wallet.model.Transaction;
import com.homates.wallet.model.Wallet;
import com.homates.wallet.repo.TransactionRepository;
import com.homates.wallet.repo.WalletRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(HouseMessage item) {
        switch(item.getMessageType()) {
            case DEL:
                System.out.println("Deleting wallets and transactions related to house "+item.getIdHouse());
                List<Wallet> wallets = walletRepository.findByIdHouse(item.getIdHouse());
                List<Transaction> transactionsDto = transactionRepository.findByIdHouse(item.getIdHouse());

                for (Wallet w : wallets) {
                    walletRepository.deleteById(w.getId());
                }

                for (Transaction t : transactionsDto) {
                    transactionRepository.deleteById(t.getId());
                }
                break;
        }
    }
}

package com.homates.wallet.controller;

import com.homates.wallet.repo.PaymentRepository;
import com.homates.wallet.repo.RefundRepository;
import com.homates.wallet.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://wallet:4200")
@RestController
@RequestMapping("/api/v1/wallet")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    RefundRepository refundRepository;
}

package com.homates.wallet.controller;

import com.homates.wallet.dto.*;
import com.homates.wallet.model.Payment;
import com.homates.wallet.model.Refund;
import com.homates.wallet.model.Transaction;
import com.homates.wallet.model.Wallet;
import com.homates.wallet.repo.PaymentRepository;
import com.homates.wallet.repo.RefundRepository;
import com.homates.wallet.repo.TransactionRepository;
import com.homates.wallet.repo.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    WalletRepository walletRepository;

    @PostMapping("/transaction/add-payment")
    public ResponseEntity<String> addItemPayment(@RequestBody PaymentDto paymentDto) {
        System.out.println("Creating a new payment...");

        Payment transaction = dtoTOPayment(paymentDto, new Payment());

        // update wallet balance
        int idHouse = paymentDto.getIdHouse();
        float amount = paymentDto.getAmount() / paymentDto.getUsernameSplit().size();
        updateWalletBalance(paymentDto.getUsernamePay(), idHouse, paymentDto.getAmount());
        for (String user: paymentDto.getUsernameSplit())
            updateWalletBalance(user, idHouse, -amount);

        paymentRepository.save(transaction);
        return new ResponseEntity<>("Payment added.", HttpStatus.OK);
    }

    @PostMapping("/transaction/add-refund")
    public ResponseEntity<String> addItemRefund(@RequestBody RefundDto refundDto) {
        System.out.println("Creating a new refund...");

        Refund transaction = dtoTORefund(refundDto, new Refund());

        // update wallet balance
        int idHouse = refundDto.getIdHouse();
        float amount = refundDto.getAmount();
        updateWalletBalance(refundDto.getUsernameFrom(), idHouse, amount);
        updateWalletBalance(refundDto.getUsernameTo(), idHouse, -amount);

        refundRepository.save(transaction);
        return new ResponseEntity<>("Refund added.", HttpStatus.OK);
    }

    @GetMapping("/transaction/house/{idHouse}")
    public ResponseEntity<List<Transaction>> getItems(@PathVariable("idHouse") int idHouse) {
        System.out.println("Getting house transactions...");
        List<Transaction> transactionsDto = transactionRepository.findByIdHouse(idHouse);

        return new ResponseEntity<>(transactionsDto, HttpStatus.OK);
    }

    @PutMapping("/transaction/update-payment/{id}")
    public ResponseEntity<String> updateItemPayment(@PathVariable("id") int id, @RequestBody PaymentDto paymentDto) {
        System.out.println("Updating payment...");

        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty())
            return new ResponseEntity<>("Payment not found.", HttpStatus.NOT_FOUND);

        Payment _currentTransaction = payment.get();
        int _currentIdHouse = _currentTransaction.getIdHouse();
        float _currentAmount = _currentTransaction.getAmount() / _currentTransaction.getUsernameSplit().size();
        float _currentAmountPaid = _currentTransaction.getAmount();
        String _currentUsernamePay = _currentTransaction.getUsernamePay();
        int idHouse = paymentDto.getIdHouse();
        float amount = paymentDto.getAmount() / paymentDto.getUsernameSplit().size();

        _currentTransaction.setDescription(paymentDto.getDescription());
        _currentTransaction.setAmount(paymentDto.getAmount());
        _currentTransaction.setDate(paymentDto.getDate());
        _currentTransaction.setIdHouse(paymentDto.getIdHouse());

        _currentTransaction.setUsernamePay(paymentDto.getUsernamePay());
        _currentTransaction.setUsernameSplit(paymentDto.getUsernameSplit());

        // update wallet balance
        updateWalletBalance(_currentUsernamePay, _currentIdHouse, -_currentAmountPaid);
        for (String user: paymentDto.getUsernameSplit()){
            updateWalletBalance(user, _currentIdHouse, _currentAmount);
        }
        updateWalletBalance(paymentDto.getUsernamePay(), idHouse, paymentDto.getAmount());
        for (String user: paymentDto.getUsernameSplit())
            updateWalletBalance(user, idHouse, -amount);


        paymentRepository.save(_currentTransaction);
        return new ResponseEntity<>("Payment updated.", HttpStatus.OK);
    }

    @PutMapping("/transaction/update-refund/{id}")
    public ResponseEntity<String> updateItemRefund(@PathVariable("id") int id, @RequestBody RefundDto refundDto) {
        System.out.println("Updating refund...");

        Optional<Refund> refund = refundRepository.findById(id);
        if (refund.isEmpty())
            return new ResponseEntity<>("Refund not found.", HttpStatus.NOT_FOUND);

        Refund _currentTransaction = refund.get();
        int _currentIdHouse = _currentTransaction.getIdHouse();
        float _currentAmount = _currentTransaction.getAmount();
        String _currentUsernameTo = _currentTransaction.getUsernameTo();
        String _currentUsernameFrom = _currentTransaction.getUsernameFrom();
        int idHouse = refundDto.getIdHouse();
        float amount = refundDto.getAmount();

        _currentTransaction.setDescription(refundDto.getDescription());
        _currentTransaction.setAmount(refundDto.getAmount());
        _currentTransaction.setDate(refundDto.getDate());
        _currentTransaction.setIdHouse(refundDto.getIdHouse());

        _currentTransaction.setUsernameFrom(refundDto.getUsernameFrom());
        _currentTransaction.setUsernameTo(refundDto.getUsernameTo());

        // update wallet balance
        updateWalletBalance(_currentUsernameFrom, _currentIdHouse, -_currentAmount);
        updateWalletBalance(_currentUsernameTo, _currentIdHouse, _currentAmount);
        updateWalletBalance(refundDto.getUsernameFrom(), idHouse, amount);
        updateWalletBalance(refundDto.getUsernameTo(), idHouse, -amount);

        refundRepository.save(_currentTransaction);
        return new ResponseEntity<>("Refund updated.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/transaction/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int id) {
        System.out.println("Deleting transaction...");

        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            Transaction _currentTransaction = transaction.get();
            int idHouse = _currentTransaction.getIdHouse();
            float amount;

            // update wallet balance
            if (_currentTransaction instanceof Payment){
                amount = _currentTransaction.getAmount() / ((Payment) _currentTransaction).getUsernameSplit().size();
                updateWalletBalance(((Payment) _currentTransaction).getUsernamePay(), idHouse, -_currentTransaction.getAmount());
                for (String user: ((Payment) _currentTransaction).getUsernameSplit())
                    updateWalletBalance(user, idHouse, amount);
            } else {
                amount = _currentTransaction.getAmount();
                updateWalletBalance(((Refund) _currentTransaction).getUsernameFrom(), idHouse, -amount);
                updateWalletBalance(((Refund) _currentTransaction).getUsernameTo(), idHouse, amount);
            }

            transactionRepository.deleteById(id);
            return new ResponseEntity<>("Transaction has been deleted.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Transaction not found.", HttpStatus.NOT_FOUND);
    }

    private Payment dtoTOPayment(PaymentDto transactionDto, Payment transaction) {

        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setIdHouse(transactionDto.getIdHouse());

        transaction.setUsernamePay(transactionDto.getUsernamePay());
        transaction.setUsernameSplit(transactionDto.getUsernameSplit());

        return transaction;
    }

    private Refund dtoTORefund(RefundDto transactionDto, Refund transaction) {

        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setIdHouse(transactionDto.getIdHouse());

        transaction.setUsernameFrom(transactionDto.getUsernameFrom());
        transaction.setUsernameTo(transactionDto.getUsernameTo());

        return transaction;
    }

    private void updateWalletBalance(String username, int idHouse, float amount){
        System.out.println("Updating wallet...");

        Optional<Wallet> wallet = walletRepository.findByUsernameAndIdHouse(username, idHouse);
        Wallet _currentWallet;
        if (wallet.isEmpty()) {
            // wallet not found
            _currentWallet = new Wallet();
            _currentWallet.setBalance(0);
            _currentWallet.setUsername(username);
            _currentWallet.setIdHouse(idHouse);
            walletRepository.save(_currentWallet);
        }
        wallet = walletRepository.findByUsernameAndIdHouse(username, idHouse);
        if (wallet.isPresent()) {
            _currentWallet = wallet.get();

            System.out.println(_currentWallet);

            _currentWallet.setBalance(_currentWallet.getBalance() + amount);
            walletRepository.save(_currentWallet);

            System.out.println(_currentWallet);
        }
    }
}



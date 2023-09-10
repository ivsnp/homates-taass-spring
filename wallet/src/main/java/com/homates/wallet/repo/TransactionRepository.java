package com.homates.wallet.repo;
import com.homates.wallet.model.Transaction;
import com.homates.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findById(int id);
    Optional<Transaction> findByIdHouse(int idHouse);
}

package com.homates.wallet.repo;
import com.homates.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findById(int id);
    List<Transaction> findByIdHouse(int idHouse);
}

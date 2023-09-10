package com.homates.wallet.repo;
import com.homates.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends TransactionRepository {
}

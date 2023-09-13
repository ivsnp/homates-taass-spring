package com.homates.wallet.repo;
import com.homates.wallet.model.Payment;
import com.homates.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer>  {
    Optional<Payment> findById(int id);
    List<Payment> findByIdHouse(int idHouse);
}

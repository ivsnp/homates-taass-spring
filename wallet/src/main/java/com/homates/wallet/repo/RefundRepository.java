package com.homates.wallet.repo;
import com.homates.wallet.model.Refund;
import com.homates.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Integer> {
    Optional<Refund> findById(int id);
    List<Refund> findByIdHouse(int idHouse);
}

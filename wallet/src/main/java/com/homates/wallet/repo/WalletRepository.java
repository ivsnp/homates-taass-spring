package com.homates.wallet.repo;
import com.homates.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Optional<Wallet> findById(int id);
    Optional<Wallet> findByUsername(String username);
    List<Wallet> findByIdHouse(int idHouse);
    Optional<Wallet> findByUsernameAndIdHouse(String username, int idHouse);
}

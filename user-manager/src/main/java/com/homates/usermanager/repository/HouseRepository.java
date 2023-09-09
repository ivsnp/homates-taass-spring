package com.homates.usermanager.repository;

import com.homates.usermanager.model.House;
import com.homates.usermanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Integer> {
    Optional<House> findById(int id);
}

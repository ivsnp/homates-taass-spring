package com.homates.userhouses.repo;
import com.homates.userhouses.model.House;
import com.homates.userhouses.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Integer> {
    Optional<House> findById(int id);
    List<House> findByOwner(UserEntity owner);

    List<House> findByOwnerOrRoomMatesContaining(UserEntity owner, UserEntity roommate);
}

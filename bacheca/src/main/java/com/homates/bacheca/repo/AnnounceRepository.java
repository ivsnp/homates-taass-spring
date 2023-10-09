package com.homates.bacheca.repo;


import com.homates.bacheca.model.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface AnnounceRepository extends JpaRepository<Announce, Integer> {
    Optional<Announce> findById(int id);
    List<Announce> findByDate(LocalDate date);
    List<Announce> findByIdHouse(int idHouse);
}

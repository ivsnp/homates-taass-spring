package com.homates.calendar.repo;

import com.homates.calendar.model.Calendar;
import com.homates.calendar.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    Optional<Calendar> findById(int id);
    Optional<Calendar> findByIdHouse(int idHouse);

    List<Event>


}

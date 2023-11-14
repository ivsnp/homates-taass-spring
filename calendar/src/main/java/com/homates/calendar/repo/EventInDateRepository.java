package com.homates.calendar.repo;

import com.homates.calendar.model.Event;
import com.homates.calendar.model.EventInDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventInDateRepository extends JpaRepository<EventInDate, Integer> {
    //@Query("from EventInDate e where e.date = :date")
    List<EventInDate> findByDate(LocalDate date);

   // @Query("select e from EventInDate e join e.event where e.date = :date ")
   // List<Event> findByStart( LocalDate date);

   // @Query("select e from Event e where not(e.end < :from and e.start > :to)")
   // Optional<List<EventInDate>> findBetweenStartAndEnd(@Param("from")LocalDate start,LocalDate end);

}





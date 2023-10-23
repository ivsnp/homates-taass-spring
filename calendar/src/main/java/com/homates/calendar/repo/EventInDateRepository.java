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
    //@Query("from EventInDate e where e.date = :date ")
    public List<EventInDate> findIn(@Param("date") LocalDate start);

    //@Query("from EventInDate e join e.event where e.date = :date ")
    public List<Event> findEventsFromStart(@Param("date") LocalDate start);

    //@Query("from Event e where not(e.end < :from or e.start > :to)")
    public Optional<List<EventInDate>> findBetween(@Param("from") LocalDate start, @Param("to") LocalDate end);

}




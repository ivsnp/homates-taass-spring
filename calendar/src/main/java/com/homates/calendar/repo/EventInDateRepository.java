package com.homates.calendar.repo;

import com.homates.calendar.model.Event;
import com.homates.calendar.model.EventInDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventInDateRepository extends CrudRepository<EventInDate, Long> {
    @Query("from EventInDate e where e.date = :date ")
    public List<EventInDate> findIn(@Param("date") LocalDate start);

    @Query("from EventInDate e join e.event where e.date = :date ")
    public List<Event> findEvents(@Param("date") LocalDate start);


}

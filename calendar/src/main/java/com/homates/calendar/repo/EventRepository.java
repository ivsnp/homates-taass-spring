package com.homates.calendar.repo;

import com.homates.calendar.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long>{

    //metodo per cercare tutti gli eventi nell'intervallo di date indicato
    @Query("from Event e where not(e.end < :from or e.start > :to)")
    public List<Event> findBetween(@Param("from")  LocalDate start, @Param("to") LocalDate end);

}


package com.homates.calendar.repo;

import com.homates.calendar.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long>{
}

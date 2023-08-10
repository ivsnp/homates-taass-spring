package com.homates.calendar.repo;

import com.homates.calendar.model.Calendar;
import org.springframework.data.repository.CrudRepository;

public interface CalendarRepository extends CrudRepository<Calendar, Long> {
}

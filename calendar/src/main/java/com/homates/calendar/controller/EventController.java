package com.homates.calendar.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.homates.calendar.model.Event;
import com.homates.calendar.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@CrossOrigin(origins = "http://calendar:4200")
@RestController
@RequestMapping("/api/v1/calendar/event")
public class EventController {

    @Autowired
    EventRepository repository;

    @GetMapping("/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Event> events(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end) {
        return repository.findBetween(start, end);
    }


    @PostMapping(value= "/events/create")
    public Event createEvent(@RequestBody Event e) {
        System.out.println("Creating new event...");
        repository.save(e).toString();
        return  e;
    }

    @PutMapping("/events/move")
    Event moveEvent(@RequestBody EventMoveParams params) {
        System.out.println("Changing date...");
        Event e = repository.findById(params.id).get();
        if (params.start!= null){
             e.setStart(LocalDate.from(params.start));
        }
        if (params.end != null) {
            e.setEnd(LocalDate.from(params.end));
        }
        repository.save(e);

        return e;
    }

    @PutMapping("/events/setColor")
    Event setColor(@RequestBody SetColorParams params) {
        System.out.println("Changing color...");
        Event e = repository.findById(params.id).get();
        e.setColor(params.color);
        repository.save(e);

        return e;
    }

    @DeleteMapping("/events/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") long id) {
        System.out.println("Deleting event...");

        Optional<Event> e = repository.findById(id);
        if (e.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>("Event has been deleted!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public static class EventMoveParams {
        public Long id;
        public LocalDate start;
        public LocalDate end;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }

}
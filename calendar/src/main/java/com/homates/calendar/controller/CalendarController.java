package com.homates.calendar.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.homates.calendar.model.Calendar;
import com.homates.calendar.model.Event;
import com.homates.calendar.model.EventInDate;
import com.homates.calendar.repo.CalendarRepository;
import com.homates.calendar.repo.EventInDateRepository;
import com.homates.calendar.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://calendar:4200")
@RestController
@RequestMapping("/api/v1/calendar")
public class CalendarController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CalendarRepository repository;
    @Autowired
    EventInDateRepository eventInDateRepository;

    //metodo per creare un calendario
    @PostMapping(value = "/create")
    public ResponseEntity<Calendar> createCalendar(@RequestBody String house){
        System.out.println("Creating new calendar...");
        Calendar calendar = new Calendar(house, new ArrayList<>());
        return new ResponseEntity<>(repository.save(calendar), HttpStatus.OK);
    }

    //metodo che recupera tutti i calendari presenti
    @GetMapping("/calendars")
    public List<Calendar> getAllCalendars() {
        System.out.println("Get all calendars...");

        List<Calendar> calendars = new ArrayList<>();
        repository.findAll().forEach(calendars::add);

        return calendars;
    }

    //metodo per creare un nuovo evento
    @PostMapping(value= "/events/create")
    public Event createEvent(@RequestBody Event e) {
        System.out.println("Creating new event...");
        eventRepository.save(e).toString();
        return  e;
    }

    //metodo per recuperare tutti gli eventi un certo range di date
    @GetMapping("/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Event> events(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end) {
        return eventRepository.findBetween(start, end);
    }

    //metodo per aggiungere l'evento nelle varie date !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<EventInDate> addEvents(Event e){

    }

    //metodo per calcolare le date da aggiungere in base alla ricorrenza????

    //metodi per modificare i vari parametri dell'evento
    @PutMapping("/events/change/color/{id}")
    Event setColor(@PathVariable("id") long id,@RequestBody String color) {
        System.out.println("Changing color...");
        Event e = eventRepository.findById(id).get();
        e.setColor(color);
        eventRepository.save(e);

        return e;
    }

    @PutMapping("/events/change/dates/{id}")
    Event moveEvent(@PathVariable("id") long id,@RequestBody EventMoveParams params) {
        System.out.println("Changing dates...");
        Event e = eventRepository.findById(id).get();
        if (params.start!= null){
            e.setStart(LocalDate.from(params.start));
        }
        if (params.end != null) {
            e.setEnd(LocalDate.from(params.end));
        }
        eventRepository.save(e);
        return e;
    }

    @PutMapping("/events/change/time/{id}")
    Event changeTimeEvent(@PathVariable("id") long id,@RequestBody LocalTime time) {
        System.out.println("Changing time...");
        Event e = eventRepository.findById(id).get();
        e.setTime(time);
        eventRepository.save(e);
        return e;
    }

    @PutMapping("/events/change/descr/{id}")
    Event changeDescrEvent(@PathVariable("id") long id, @RequestBody String descr) {
        System.out.println("Changing description...");
        Event e = eventRepository.findById(id).get();
        e.setDescription(descr);
        eventRepository.save(e);
        return e;
    }

    @PutMapping("/events/change/recur/{id}")
    Event changeRecurEvent(@PathVariable("id") long id, @RequestBody String recur) {
    //in caso di questa modifica occorre cancellare tutte le occorrenze di questo evento dalla data corrente in poi
    // presenti nel calendario e ripopolarlo con quelle nuove della ricorrenza

    }


    //metodo per cancellare un evento
    @DeleteMapping("/events/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") long id) {
        System.out.println("Deleting event...");

        Optional<Event> e = eventRepository.findById(id);
        if (e.isPresent()) {
            eventRepository.deleteById(id);
            return new ResponseEntity<>("Event has been deleted!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //metodo per cancellare una specifica istanza dell'evento per una certa data
    @DeleteMapping("/events_in_date/delete")
    public ResponseEntity<Calendar> deleteEventInDate(EventToDelete ev){
        System.out.println("Deleting event in specific date...");
        Optional<Calendar> c = repository.findByHouse(ev.house);
        if(c.isPresent()) {
            Calendar cal = c.get();
            List<EventInDate> events = eventInDateRepository.findIn(ev.date);
            Optional<Event> e = eventRepository.findById(ev.id_event);
            if (events.contains(e)) {
                EventInDate evd = events.get(events.indexOf(e));
                cal.getEvents().remove(evd);
                return new ResponseEntity<>(repository.save(cal), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //metodo per recuperare tutti gli eventi in una specifica data
    @GetMapping("/events_in_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Event> eventsInDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start) {
        return eventInDateRepository.findEvents(start);
    }



    //metodo per prendere gli eventi associati ad uno specifico user
    @GetMapping("/events_in_date/user/{id}/{name}")
    public List<EventInDate> eventsForUser(@PathVariable("id") Long id,@PathVariable("name") String user){
        Optional<Calendar> c = repository.findById(id);
        List<EventInDate> events = new ArrayList<>();
        if(c.isPresent()){
            Calendar cal = c.get();
            for(int i = 0; i < cal.getEvents().size(); i++){
                EventInDate _evd = cal.getEvents().get(i);
                if(_evd.getEvent().getUser() == user){
                    events.add(_evd);
                }
            }
        }
        return events;

    }



    //metodo per cancellare un calendario
    @DeleteMapping("/delete/{house}")
    public ResponseEntity<String> deleteCalendar(@PathVariable("house") String house) {
        System.out.println("Deleting calendar of house = " + house + "...");

        Optional<Calendar> c = repository.findByHouse(house);
        if (c.isPresent()) {
            Calendar cal = c.get();
            for(int i = 0; i < cal.getEvents().size(); i++){
                EventInDate _evd = cal.getEvents().get(i);
                eventInDateRepository.deleteById(_evd.getId());
            }
            repository.deleteById(cal.getId());
            return new ResponseEntity<>("Calendar has been deleted!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public static class EventToDelete {
        public Long id_event;
        public String house;
        public LocalDate date;
    }

    public static class EventMoveParams {
        public LocalDate start;
        public LocalDate end;
    }


}

package com.homates.calendar.controller;

import com.homates.calendar.dto.ChangeEventDto;
import com.homates.calendar.dto.EventDatesDto;
import com.homates.calendar.dto.EventDto;
import com.homates.calendar.model.Calendar;
import com.homates.calendar.model.Event;
import com.homates.calendar.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@CrossOrigin(origins = "http://calendar:4200")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/calendar/event")
public class EventController {

    @Autowired
    EventRepository eventRepository;

    //metodo per recuperare tutti gli eventi in un certo range di date
    @GetMapping("/events")
    ResponseEntity<Iterable<Event>> getEventsInDates(@RequestBody EventDatesDto eventDatesDto) {
        Iterable<Event> _currentEvents = new ArrayList<>();

        Optional<List<Event>> events = eventRepository.findBetween(eventDatesDto.getStart(),eventDatesDto.getEnd());
        if (events.isEmpty())
            return new ResponseEntity<>(_currentEvents, HttpStatus.NOT_FOUND);
        _currentEvents = events.get();
        return new ResponseEntity<>(_currentEvents, HttpStatus.OK);
    }

    //metodo per creare un nuovo evento
    @PostMapping(value= "/create")
    public ResponseEntity<String> createEvent(@RequestBody EventDto eventDto) {
        System.out.println("Creating new event...");
        Event event = new Event();
        event.setColor(eventDto.getColor());
        event.setTime(eventDto.getTime());
        event.setDescription(eventDto.getDescription());
        event.setStart(eventDto.getStart());
        event.setEnd(eventDto.getEnd());
        event.setUser(eventDto.getUser());
        event.setIdHouse(eventDto.getIdHouse());
        event.setRepetition(eventDto.getRepetition());

        eventRepository.save(event);
        return new ResponseEntity<>("New event created", HttpStatus.OK);

    }
    @GetMapping("/get_event/{id}")
    ResponseEntity<Event> getEvent(@PathVariable("id") int id) {
        System.out.println("Get event...");
        Event _currentEvent = new Event();
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty())
            return new ResponseEntity<>(_currentEvent, HttpStatus.NOT_FOUND);
        _currentEvent = event.get();
        return new ResponseEntity<>(_currentEvent, HttpStatus.OK);
    }

    //metodo per modificare i vari parametri dell'evento
    //ogni volta che si fa una modifica il Dto deve essere settato a null per tutti i valori immutati
    @PutMapping("/update/{id}")
    ResponseEntity<String> updateEvent(@PathVariable("id") int id,@RequestBody ChangeEventDto changeEventDto) {
        System.out.println("Changing event params...");
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty())
            return new ResponseEntity<>("Event not found.", HttpStatus.NOT_FOUND);

        Event _currentEvent = event.get();
        if (changeEventDto.getStart()!= null){
            _currentEvent.setStart(changeEventDto.getStart());
        }
        if (changeEventDto.getEnd()!= null){
            _currentEvent.setEnd(changeEventDto.getEnd());
        }
        if (changeEventDto.getColor()!= null){
            _currentEvent.setColor(changeEventDto.getColor());
        }
        if (changeEventDto.getDescription()!= null){
            _currentEvent.setDescription(changeEventDto.getDescription());
        }
        if (changeEventDto.getUser()!= null){
            _currentEvent.setUser(changeEventDto.getUser());
        }
        if (changeEventDto.getTime()!= null){
            _currentEvent.setTime(changeEventDto.getTime());
        }
        if (changeEventDto.getRepetition()!= null){
            _currentEvent.setRepetition(changeEventDto.getRepetition());
        }

        eventRepository.save(_currentEvent);

        return new ResponseEntity<>("Event updated.", HttpStatus.OK);
    }


    //metodo per cancellare un evento
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") int id) {
        System.out.println("Deleting event...");

        Optional<Event> e = eventRepository.findById(id);
        if (e.isPresent()) {
            eventRepository.deleteById(id);
            return new ResponseEntity<>("Event has been deleted!", HttpStatus.OK);
        }else {
        return new ResponseEntity<String>("Event not found",HttpStatus.NOT_FOUND);}
    }


}
package com.homates.calendar.controller;

import com.homates.calendar.dto.AddEventDto;
import com.homates.calendar.dto.CalendarDto;
import com.homates.calendar.dto.EventInDateDto;
import com.homates.calendar.dto.EventsForUserDto;
import com.homates.calendar.model.Calendar;
import com.homates.calendar.model.Event;
import com.homates.calendar.model.EventInDate;
import com.homates.calendar.repo.CalendarRepository;
import com.homates.calendar.repo.EventInDateRepository;
import com.homates.calendar.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@CrossOrigin(origins = "http://calendar:4200")
//@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*", allowCredentials = "true")
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
    public ResponseEntity<String> createCalendar(@RequestBody CalendarDto calendarDto){
        System.out.println("Creating new calendar...");
        Calendar _currentCalendar = new Calendar();
        _currentCalendar.setIdHouse(calendarDto.getIdHouse());
        _currentCalendar.setEvents(new ArrayList<>());
        repository.save(_currentCalendar);
        return new ResponseEntity<>("New Calendar added.", HttpStatus.OK);
    }

    //metodo che recupera tutti i calendari presenti
    @GetMapping("/all")
    public ResponseEntity<List<Calendar>> getAllCalendars() {
        System.out.println("Get all calendars...");

        List<Calendar> calendars = new ArrayList<>();
        repository.findAll().forEach(calendars::add);
        return new ResponseEntity<>(calendars, HttpStatus.OK);
    }


    //metodo che recupera il calendario della casa specifica
    @GetMapping("/my-calendar/{house}")
    public ResponseEntity<Calendar> getCalendar(@PathVariable("house") int house) {
        System.out.println("Get calendar of house "+house+"...");
        Calendar _currentCalendar = new Calendar();
        Optional<Calendar> calendar = repository.findByIdHouse(house);
        if (calendar.isEmpty())
            return new ResponseEntity<>(_currentCalendar, HttpStatus.NOT_FOUND);
        _currentCalendar = calendar.get();
        return new ResponseEntity<>(_currentCalendar, HttpStatus.OK);
    }


    //metodo per aggiungere l'evento nelle varie date !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostMapping(value = "/add_event/weekly/{day}/{id}")
    public ResponseEntity<String> addWeeklyEvents(@PathVariable("day") int day,@PathVariable("id") int id){
        System.out.println("Add event every recurrence of day in week...");

        //Optional<Event> addEvent = eventRepository.findById(addEventDto.getIdEvent());

        Optional<Event> addEvent = eventRepository.findById(id);
        if(addEvent.isEmpty())
            return new ResponseEntity<>("The event "+id+" doesn't exists", HttpStatus.NOT_FOUND);
        Event _currEvent= addEvent.get();
        Calendar cal = repository.findByIdHouse(_currEvent.getIdHouse()).get();
        LocalDate _currDate = _currEvent.getStart().with(TemporalAdjusters.nextOrSame(DayOfWeek.of(day)));
        while(!_currDate.isAfter(_currEvent.getEnd())){
            if(_currDate.getDayOfWeek().equals(DayOfWeek.of(day))) {
                EventInDate _currEventInDate = new EventInDate();
                _currEventInDate.setEvent(_currEvent);
                _currEventInDate.setDate(_currDate);
                eventInDateRepository.save(_currEventInDate);
                cal.getEvents().add(_currEventInDate);
            }
            _currDate = _currEvent.getEnd().plusWeeks(1);
        }

        repository.save(cal);
        return new ResponseEntity<>("New event in date added.", HttpStatus.OK);
    }

    @PostMapping(value = "/add_event/monthly/{month}")
    public ResponseEntity<String> addMonthlyEvents(@PathVariable("month") int month, AddEventDto addEventDto){
        return new ResponseEntity<>("New event in date added.", HttpStatus.OK);
    }

    @PostMapping(value = "/add_event/yearly")
    public ResponseEntity<String> addYearlyEvents(AddEventDto addEventDto){
        return new ResponseEntity<>("New event in date added.", HttpStatus.OK);
    }

    @PostMapping(value = "/add_event/everyday")
    public ResponseEntity<String> addEverydayEvents(AddEventDto addEventDto){
        return new ResponseEntity<>("New event in date added.", HttpStatus.OK);
    }


    //metodo per cancellare una specifica istanza dell'evento per una certa data
    @DeleteMapping("/events_in_date/delete/{id}")
    public ResponseEntity<String> deleteEventInDate(@PathVariable("id") int id,EventInDateDto eventInDateDto){
        System.out.println("Deleting event in specific date...");
        Optional<Calendar> c = repository.findByIdHouse(eventInDateDto.getIdHouse());
        if(c.isPresent()) {
            Calendar cal = c.get();
            Optional<List<EventInDate>> _events = eventInDateRepository.findIn(eventInDateDto.getDate());
            if (_events.isEmpty())
                return new ResponseEntity<>("events not found", HttpStatus.NOT_FOUND);
            List<EventInDate> events = _events.get();
            Optional<Event> e = eventRepository.findById(id);
            if (events.contains(e)) {
                EventInDate evd = events.get(events.indexOf(e));
                cal.getEvents().remove(evd);
                return new ResponseEntity<>("Calendar updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
    }


    //metodo per recuperare tutti gli eventi in una specifica data
  //  @GetMapping("/events_in_date")
  //  public ResponseEntity<> eventsInDate(EventInDateDto eventInDateDto) {
  //      return eventInDateRepository.f;
  //  }



    //metodo per prendere gli eventi associati ad uno specifico user

    //DA RIVEDEREEEEEEEEEE
    @GetMapping("/events_in_date/user")
    public ResponseEntity<List<EventInDate>> eventsForUser(@RequestBody EventsForUserDto efuDto){
        //prendo l'utente e il calendario
        //prendo tutti gli eventi associati a quell'utente
        //prendo tutte le ricorrenze di quell'evento

        Optional<Calendar> c = repository.findById(efuDto.getIdCalendar());
        List<EventInDate> events = new ArrayList<>();
        if(c.isPresent()){
            Calendar cal = c.get();
            for(int i = 0; i < cal.getEvents().size(); i++){
                EventInDate _evd = cal.getEvents().get(i);
                if(_evd.getEvent().getUser().equals(efuDto.getIdUser())){
                    events.add(_evd);
                }
            }
        }
        return new ResponseEntity<>(events, HttpStatus.NOT_FOUND);

    }


    //metodo per cancellare un calendario
    @DeleteMapping("/delete/{house}")
    public ResponseEntity<String> deleteCalendar(@PathVariable("house") int house) {
        System.out.println("Deleting calendar of house = " + house + "...");

        Optional<Calendar> c = repository.findByIdHouse(house);
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

    //metodo che verifica se ci sono modifiche nell'evento, cancella tutte le ricorrenze dell'evento e le aggiorna con i nuovi parametri
}

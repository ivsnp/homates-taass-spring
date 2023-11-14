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


    //metodo per aggiungere un evento a cadenza settimanale in base al giorno della settimana definito, nel range da start a end

    @PostMapping(value = "/add_event/weekly/{id}")
    public ResponseEntity<String> addWeeklyEvents(@PathVariable("id") int id,@RequestBody AddEventDto addEventDto){
        System.out.println("Add event every recurrence of day in week...");

        Optional<Event> addEvent = eventRepository.findById(id);
        if(addEvent.isEmpty())
            return new ResponseEntity<>("The event "+id+" doesn't exists", HttpStatus.NOT_FOUND);
        Event _currEvent= addEvent.get();

        Optional<Calendar> _currCal = repository.findByIdHouse(_currEvent.getIdHouse());
        if(_currCal.isEmpty())
            return new ResponseEntity<>("The calendar doesn't exists", HttpStatus.NOT_FOUND);
        Calendar cal = _currCal.get();

        LocalDate _currDate = _currEvent.getStart().with(TemporalAdjusters.nextOrSame(DayOfWeek.of(addEventDto.getDay_month())));
        while(!_currDate.isAfter(_currEvent.getEnd())){
                EventInDate _currEventInDate = new EventInDate();
                _currEventInDate.setEvent(_currEvent);
                _currEventInDate.setDate(_currDate);
                eventInDateRepository.save(_currEventInDate);
                cal.getEvents().add(_currEventInDate);
                _currDate = _currDate.plusWeeks(1);
        }

        repository.save(cal);
        return new ResponseEntity<>("New events in dates added.", HttpStatus.OK);
    }

    //metodo per aggiungere un evento a cadenza mensile in base al giorno definito, nel range da start a end
    //se il month è 0 vuol dire che si ripete tutti i mesi, altrimenti il numero corrisponde al mese prescelto per la ripetizione
    @PostMapping(value = "/add_event/monthly/{id}")
    public ResponseEntity<String> addMonthlyEvents(@PathVariable("id") int id,@RequestBody AddEventDto addEventDto){
        System.out.println("Add event every recurrence of day in months...");
        //recupero evento
        Optional<Event> addEvent = eventRepository.findById(id);
        if(addEvent.isEmpty())
            return new ResponseEntity<>("The event "+id+" doesn't exists", HttpStatus.NOT_FOUND);
        Event _currEvent= addEvent.get();
        //recupero calendario dall'id della casa dell'evento
        Optional<Calendar> _currCal = repository.findByIdHouse(_currEvent.getIdHouse());
        if(_currCal.isEmpty())
            return new ResponseEntity<>("The calendar doesn't exists", HttpStatus.NOT_FOUND);
        Calendar cal = _currCal.get();
        //creo un evento nella data di inizio dell'evento
        LocalDate _currDate = _currEvent.getStart();
        //se il codice è zero, vuol dire che la ripetizione è tutti i mesi fino alla data di fine, nella stessa data
        if (addEventDto.getDay_month()==0) {
            while (!_currDate.isAfter(_currEvent.getEnd())) {
                EventInDate _currEventInDate = new EventInDate();
                _currEventInDate.setEvent(_currEvent);
                _currEventInDate.setDate(_currDate);
                eventInDateRepository.save(_currEventInDate);
                cal.getEvents().add(_currEventInDate);
                _currDate = _currDate.plusMonths(1);
            }
        }else{
            _currDate = _currDate.withMonth(addEventDto.getDay_month());
            while (!_currDate.isAfter(_currEvent.getEnd())) {
                EventInDate _currEventInDate = new EventInDate();
                _currEventInDate.setEvent(_currEvent);
                _currEventInDate.setDate(_currDate);
                eventInDateRepository.save(_currEventInDate);
                cal.getEvents().add(_currEventInDate);
                _currDate = _currDate.plusYears(1);
            }
        }
        repository.save(cal);
        return new ResponseEntity<>("New events in dates added.", HttpStatus.OK);

    }

    @PostMapping(value = "/add_event/yearly/{id}")
    public ResponseEntity<String> addYearlyEvents(@PathVariable("id") int id){
        System.out.println("Add event every recurrence of day in years...");

        //recupero evento
        Optional<Event> addEvent = eventRepository.findById(id);
        if(addEvent.isEmpty())
            return new ResponseEntity<>("The event "+id+" doesn't exists", HttpStatus.NOT_FOUND);
        Event _currEvent= addEvent.get();

        //recupero calendario dall'id della casa dell'evento
        Optional<Calendar> _currCal = repository.findByIdHouse(_currEvent.getIdHouse());
        if(_currCal.isEmpty())
            return new ResponseEntity<>("The calendar doesn't exists", HttpStatus.NOT_FOUND);
        Calendar cal = _currCal.get();

        LocalDate _currDate = _currEvent.getStart();
        while (!_currDate.isAfter(_currEvent.getEnd())) {
            EventInDate _currEventInDate = new EventInDate();
            _currEventInDate.setEvent(_currEvent);
            _currEventInDate.setDate(_currDate);
            eventInDateRepository.save(_currEventInDate);
            cal.getEvents().add(_currEventInDate);
            _currDate = _currDate.plusYears(1);
        }
        repository.save(cal);
        return new ResponseEntity<>("New events in dates added.", HttpStatus.OK);
    }

    @PostMapping(value = "/add_event/everyday/{id}")
    public ResponseEntity<String> addEverydayEvents(@PathVariable("id") int id){
        System.out.println("Add event every recurrence of day in years...");

        //recupero evento
        Optional<Event> addEvent = eventRepository.findById(id);
        if(addEvent.isEmpty())
            return new ResponseEntity<>("The event "+id+" doesn't exists", HttpStatus.NOT_FOUND);
        Event _currEvent= addEvent.get();

        //recupero calendario dall'id della casa dell'evento
        Optional<Calendar> _currCal = repository.findByIdHouse(_currEvent.getIdHouse());
        if(_currCal.isEmpty())
            return new ResponseEntity<>("The calendar doesn't exists", HttpStatus.NOT_FOUND);
        Calendar cal = _currCal.get();

        LocalDate _currDate = _currEvent.getStart();
        while (!_currDate.isAfter(_currEvent.getEnd())) {
            EventInDate _currEventInDate = new EventInDate();
            _currEventInDate.setEvent(_currEvent);
            _currEventInDate.setDate(_currDate);
            eventInDateRepository.save(_currEventInDate);
            cal.getEvents().add(_currEventInDate);
            _currDate = _currDate.plusDays(1);
        }
        repository.save(cal);
        return new ResponseEntity<>("New events in dates added.", HttpStatus.OK);
    }


    //metodo per cancellare una specifica istanza dell'evento per una certa data
    @DeleteMapping("/events_in_date/delete/{id}")
    public ResponseEntity<String> deleteEventInDate(@PathVariable("id") int id,@RequestBody EventInDateDto eventInDateDto){
        System.out.println("Deleting event in specific date...");
        Optional<Calendar> c = repository.findByIdHouse(eventInDateDto.getIdHouse());
        if(c.isEmpty())
            return new ResponseEntity<>("Calendar not found", HttpStatus.NOT_FOUND);
        Calendar cal = c.get();

        List<EventInDate> events = eventInDateRepository.findByDate(eventInDateDto.getDate());
        System.out.println(events);
        if (events.isEmpty())
            return new ResponseEntity<>("Events not found", HttpStatus.NOT_FOUND);
        //List<EventInDate> events = _events.get();

        for (EventInDate eid : events) {
            if (eid.getEvent().getId() == id) {
                cal.getEvents().remove(eid);
                eventInDateRepository.delete(eid);
                //eventInDateRepository.deleteById(eid.getId());
                repository.save(cal);
                return new ResponseEntity<>("Calendar updated", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
    }

    //metodo per prendere gli eventi associati ad uno specifico user

    //DA RIVEDEREEEEEEEEEE
    @GetMapping("/events_in_date/user")
    public ResponseEntity<List<EventInDate>> eventsForUser(@RequestBody EventsForUserDto efuDto){
        //prendo l'utente e il calendario
        //prendo tutti gli eventi associati a quell'utente
        //prendo tutte le ricorrenze di quell'evento
        System.out.println("Finding events for specific user...");
        Optional<Calendar> c = repository.findByIdHouse(efuDto.getIdHouse());
        List<EventInDate> events = new ArrayList<>();
        if(c.isPresent()){
            Calendar cal = c.get();
            for (EventInDate eid : cal.getEvents()){
                if((eid.getEvent().getUser()).equals(efuDto.getUser())){
                    events.add(eid);
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
        if(c.isPresent()){
            Calendar cal = c.get();
            for (EventInDate eid : cal.getEvents()){
                eventRepository.deleteById(eid.getEvent().getId());
            }
            repository.deleteById(cal.getId());
            return new ResponseEntity<>("Calendar has been deleted!", HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

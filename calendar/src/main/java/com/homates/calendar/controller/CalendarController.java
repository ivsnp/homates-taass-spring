package com.homates.calendar.controller;

import com.homates.calendar.dto.*;
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
                System.out.println(_currEventInDate.getDate());
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
        System.out.println("Add event one a year...");

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
    @DeleteMapping("/events_in_date/delete/{id}/{house}")
    public ResponseEntity<String> deleteEventInDate(@PathVariable("id") int id,@PathVariable("house") int idHouse){
        System.out.println("Deleting event in specific date...");
        Optional<Calendar> c = repository.findByIdHouse(idHouse);
        if(c.isEmpty())
            return new ResponseEntity<>("Calendar not found", HttpStatus.NOT_FOUND);
        Calendar cal = c.get();
        List<EventInDate> _events = cal.getEvents();
        if (_events.isEmpty())
            return new ResponseEntity<>("Events not found", HttpStatus.NOT_FOUND);
        for (EventInDate eid : _events) {
            if (eid.getId() == id) {
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


    //metodo per recuperare tutti gli eventi in un certo range di date
    @GetMapping("event/events")
    ResponseEntity<Iterable<Event>> getEventsInDates(@RequestBody EventDatesDto eventDatesDto) {
        Iterable<Event> _currentEvents = new ArrayList<>();

        Optional<List<Event>> events = eventRepository.findBetweenStartAndEnd(eventDatesDto.getStart(),eventDatesDto.getEnd());
        if (events.isEmpty())
            return new ResponseEntity<>(_currentEvents, HttpStatus.NOT_FOUND);
        _currentEvents = events.get();
        return new ResponseEntity<>(_currentEvents, HttpStatus.OK);
    }

    //metodo per creare un nuovo evento
    @PostMapping(value= "event/create")
    public ResponseEntity<Event> createEvent(@RequestBody EventDto eventDto) {
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
        return new ResponseEntity<>(event, HttpStatus.OK);

    }
    @GetMapping("event/get_event/{id}")
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
    @PutMapping("event/update/{id}")
    ResponseEntity<String> updateEvent(@PathVariable("id") int id,@RequestBody ChangeEventDto changeEventDto) {
        System.out.println("Changing event params...");
        boolean nuovo = false;

        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty())
            return new ResponseEntity<>("Event not found.", HttpStatus.NOT_FOUND);

        Event _currentEvent = event.get();

        if (changeEventDto.getStart() != null) {
            _currentEvent.setStart(changeEventDto.getStart());
            nuovo = true;
        }
        if (changeEventDto.getEnd() != null) {
            _currentEvent.setEnd(changeEventDto.getEnd());
            nuovo = true;
        }
        if (changeEventDto.getColor() != null) {
            _currentEvent.setColor(changeEventDto.getColor());
        }
        if (changeEventDto.getDescription() != null) {
            _currentEvent.setDescription(changeEventDto.getDescription());
        }
        if (changeEventDto.getUser() != null) {
            _currentEvent.setUser(changeEventDto.getUser());
        }
        if (changeEventDto.getTime() != null) {
            _currentEvent.setTime(changeEventDto.getTime());
        }
        if (changeEventDto.getRepetition() != null) {
            _currentEvent.setRepetition(changeEventDto.getRepetition());
            nuovo = true;
        }
        eventRepository.save(_currentEvent);

        if (nuovo) {
            EventDto ev =new EventDto();
            ev.setDescription(_currentEvent.getDescription());
            ev.setTime(_currentEvent.getTime());
            ev.setUser(_currentEvent.getUser());
            ev.setStart(_currentEvent.getStart());
            ev.setIdHouse(_currentEvent.getIdHouse());
            ev.setColor(_currentEvent.getColor());
            ev.setEnd(_currentEvent.getEnd());
            ev.setRepetition(_currentEvent.getRepetition());

            Event change  = createEvent(ev).getBody();

            AddEventDto dto = new AddEventDto();
            dto.setDay_month(changeEventDto.getFrequency());
            System.out.println(ev.getRepetition());

            if (change.getRepetition().equals("everyday")) {
                addEverydayEvents(change.getId());
            } else if (change.getRepetition().equals("weekly")) {
                System.out.println(dto.getDay_month());
                addWeeklyEvents(change.getId(), dto);
            }else if (change.getRepetition().equals("monthly")) {
                System.out.println(dto.getDay_month());
                addMonthlyEvents(change.getId(), dto);
            } else if (change.getRepetition().equals("yearly")) {
                addYearlyEvents(change.getId());
            }
            eventRepository.deleteById(id);
        }

        return new ResponseEntity<>("Event updated.", HttpStatus.OK);

    }


    //metodo per cancellare un evento
    @DeleteMapping("event/delete/{id}")
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

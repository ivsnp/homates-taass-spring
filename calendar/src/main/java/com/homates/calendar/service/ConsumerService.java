package com.homates.calendar.service;

import com.homates.calendar.dto.HouseMessage;
import com.homates.calendar.model.Calendar;
import com.homates.calendar.model.EventInDate;
import com.homates.calendar.repo.CalendarRepository;
import com.homates.calendar.repo.EventInDateRepository;
import com.homates.calendar.repo.EventRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumerService {

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    EventRepository eventRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(HouseMessage item) {
        switch(item.getMessageType()) {
            case DEL:
                System.out.println("Deleting calendar related to house "+item.getIdHouse());
                Optional<Calendar> c = calendarRepository.findByIdHouse(item.getIdHouse());
                if(c.isPresent()) {
                    Calendar cal = c.get();
                    for (EventInDate eid : cal.getEvents()) {
                        eventRepository.deleteById(eid.getEvent().getId());
                    }
                    calendarRepository.deleteById(cal.getId());
                }
                break;
            case CREATE:
                System.out.println("Creating calendar related to house "+item.getIdHouse());
                Calendar _currentCalendar = new Calendar();
                _currentCalendar.setIdHouse(item.getIdHouse());
                _currentCalendar.setEvents(new ArrayList<>());
                calendarRepository.save(_currentCalendar);
                break;
            default:
                System.out.println("MessageType not found");
        }
    }
}

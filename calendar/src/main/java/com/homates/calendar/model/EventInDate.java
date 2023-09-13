package com.homates.calendar.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "event_in_date")
public class EventInDate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event")
     private Event event;

    @Column(name= "date")
    private LocalDate date;

 /*   @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "calendar")
    Calendar calendar;*/

    public EventInDate() {
    }

    public EventInDate(Event event, LocalDate date) {
        this.event = event;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvents(Event events) {
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}

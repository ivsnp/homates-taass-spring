package com.homates.calendar.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
//import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "event_in_data")
public class EventInData {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event")
    List<Event> events;

    @Column(name= "date")
    LocalDate date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "calendar")
    Calendar calendar;

    public EventInData() {
    }

    public EventInData(List<Event> events, LocalDate date, Calendar calendar) {
        this.events = events;
        this.date = date;
        this.calendar = calendar;
    }

    public Long getId() {
        return id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}

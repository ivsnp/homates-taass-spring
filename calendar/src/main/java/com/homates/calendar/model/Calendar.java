package com.homates.calendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
//import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String name;

    //da usare quando si avrà l'user e la casa @neToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "house")
    private String house;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "events")
    private List<EventInData> events;

    public Calendar() {
    }

    public Calendar(String name, String house, List<EventInData> events) {
        this.name = name;
        this.house = house;
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public List<EventInData> getEvents() {
        return events;
    }

    public void setEvents(List<EventInData> events) {
        this.events = events;
    }
}
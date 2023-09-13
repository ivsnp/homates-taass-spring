package com.homates.calendar.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //da usare quando si avrà l'user e la casa @neToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "house")
    private String house;

    @OneToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "events_list")
    private List<EventInDate> events;


    public Calendar() {
    }

    public Calendar( String house, List<EventInDate> events) {
        this.house = house;
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public List<EventInDate> getEvents() {
        return events;
    }

    public void setEvents(List<EventInDate> events) {
        this.events = events;
    }

    //eventi nella data specifica

    //lista di eventi di un user

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", house='" + house + '\'' +
                ", events=" + events +
                '}';
    }
}

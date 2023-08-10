package com.homates.calendar.model;

//import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;


@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column(name= "description")
    String description;

    @Column(name= "user")//questa sarà una join con l'utenza registrata in accounting
    String user;

    @Column(name = "start_date")
    LocalDate start;

    @Column(name = "end_date")
    LocalDate end;

    @Column(name = "time")
    LocalTime time;

    @Column(name= "repetition")
    String repetiton;

    @Column(name= "color")
    String color;

    public Event(String description, String user, LocalDate start, LocalDate end, LocalTime time, String repetiton, String color) {
        this.description = description;
        this.user = user;
        this.start = start;
        this.end = end;
        this.time = time;
        this.repetiton = repetiton;
        this.color = color;
    }

    public Event() { }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getRepetiton() {
        return repetiton;
    }

    public void setRepetiton(String repetiton) {
        this.repetiton = repetiton;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", user='" + user + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", time=" + time +
                ", repetiton='" + repetiton + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

package com.homates.calendar.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;


@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name= "description")
    private String description;

    @Column(name= "username")//questa sarà una join con l'utenza registrata in accounting
    private String user;

    @Column(name = "start_date")
    private LocalDate start;

    @Column(name = "end_date")
    private LocalDate end;

    @Column(name = "time")
    private LocalTime time;

    @Column(name= "repetition")
    private String repetition;

    @Column(name= "color")
    private String colors;

    public Event(String description, String user, LocalDate start, LocalDate end, LocalTime time, String repetition, String colors) {
        this.description = description;
        this.user = user;
        this.start = start;
        this.end = end;
        this.time = time;
        this.repetition = repetition;
        this.colors = colors;
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

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getColor() {
        return colors;
    }

    public void setColor(String colors) {
        this.colors = colors;
    }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id mamt=" + id +
                ", description='" + description + '\'' +
                ", user='" + user + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", time=" + time +
                ", repetition='" + repetition + '\'' +
                ", color='" + colors + '\'' +
                '}';
    }
}

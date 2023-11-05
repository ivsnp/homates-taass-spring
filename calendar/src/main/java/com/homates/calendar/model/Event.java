package com.homates.calendar.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name= "description")
    private String description;

    @Column(name= "assigned_user")
    private String user;

    @Column(name= "house")
    private int idHouse;

    @Column(name = "start_date")
    private LocalDate start;

    @Column(name = "end_date")
    private LocalDate end;

    @Column(name = "time")
    private LocalTime time;

   @Column(name = "repetition")
    private String repetition;

    @Column(name= "color")
    private String color;

}

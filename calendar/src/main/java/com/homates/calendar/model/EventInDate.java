package com.homates.calendar.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "event_in_date")
@Data
@NoArgsConstructor
public class EventInDate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
     //BasicSchedule event;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event")
    private Event event;

    @Column(name = "date")
    private LocalDate date;

}

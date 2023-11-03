package com.homates.calendar.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "calendar")
@Data
@NoArgsConstructor
public class Calendar {

    //This class manage the user-houses Calendar in which every user of the specific house can add
    //their events that can be associated to themselves or to other users of the house, and is shared
    //with the whole house. Also the event can be recurrent
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //da usare quando si avrà l'user e la casa @neToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "house")
    private int idHouse;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "events_list")
    private List<EventInDate> events;
    //private List<Event> events;
}

package com.homates.bacheca.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "announce")
@Data
@NoArgsConstructor
public class Announce{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idHouse;

    @Column(name = "description")
    private String description;

    @Column(name="username")
    private String user;

    @Column(name="date")
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="documents")
    private List<Document> documents; //inspection 'persistent attribute type checks' options

}



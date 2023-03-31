package com.homates.accounting.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email")
    private String email;

    public User() {
    }

    public User(long id, String email) {
        this.id = id;
        this.email = email;
    }
}

package com.homates.accounting.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "pwd")
    private String pwd;

    public User() {
    }

    public User(long id, String email, String pwd) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
    }
}

package com.homates.usermanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "house")
@Data
@NoArgsConstructor
public class House {
    // TODO: add owner

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "house_users", joinColumns = @JoinColumn(name = "house_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<UserEntity> users = new ArrayList<>();
}

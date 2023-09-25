package com.homates.userhouses.model;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner", referencedColumnName = "id", nullable=false)
    private UserEntity owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "house_users", joinColumns = @JoinColumn(name = "house_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<UserEntity> roomMates = new ArrayList<>();
}

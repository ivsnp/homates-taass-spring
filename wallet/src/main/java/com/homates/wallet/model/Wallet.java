package com.homates.wallet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet")
@Data
@NoArgsConstructor
public class Wallet {

    // This class manage the user-houses wallet in order to store the wallet balance
    // without calculate it every time a client request the balance.
    // WARNING: being a duplicate, it is necessary to manage the consistency

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float balance = 0;

    private String username;

    private int idHouse;
}

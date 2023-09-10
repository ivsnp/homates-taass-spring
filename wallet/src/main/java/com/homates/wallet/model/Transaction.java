package com.homates.wallet.model;

import com.homates.wallet.Currency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    private float amount;  // importo

    private Currency currency;

    private LocalDate date;

    private int idHouse;
}

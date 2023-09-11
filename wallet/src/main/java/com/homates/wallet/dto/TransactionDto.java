package com.homates.wallet.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class TransactionDto {
    private String description;
    private float amount;  // importo
    private LocalDate date;
    private int idHouse;
}

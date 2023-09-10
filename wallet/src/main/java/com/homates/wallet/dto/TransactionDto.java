package com.homates.wallet.dto;

import com.homates.wallet.Currency;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class TransactionDto {
    private String description;
    private float amount;  // importo
    private Currency currency;
    private LocalDate date;
    private int idHouse;
}

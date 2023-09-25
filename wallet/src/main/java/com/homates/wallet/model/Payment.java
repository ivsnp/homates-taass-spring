package com.homates.wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
public class Payment extends Transaction{
    private String usernamePay;  // who pay
    private List<String> usernameSplit;  // roommates that split the payment
}

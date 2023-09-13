package com.homates.wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "refund")
@Data
@NoArgsConstructor
public class Refund extends Transaction{
    private String usernameFrom;  // who pay
    private String usernameTo;  // who receive
}

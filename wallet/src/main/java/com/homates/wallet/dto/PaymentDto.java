package com.homates.wallet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentDto extends TransactionDto {
    private String usernamePay;  // who pay
    private List<String> usernameSplit;  // roommates that split the payment
}

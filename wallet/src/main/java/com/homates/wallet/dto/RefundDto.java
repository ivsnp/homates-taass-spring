package com.homates.wallet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefundDto extends TransactionDto  {
    private String usernameFrom;  // who pay
    private String usernameTo;  // who receive
}

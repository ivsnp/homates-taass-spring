package com.homates.wallet.dto;

import lombok.Data;

@Data
public class WalletBalanceDto {
    private String username;
    private int idHouse;
    private float balance;
}

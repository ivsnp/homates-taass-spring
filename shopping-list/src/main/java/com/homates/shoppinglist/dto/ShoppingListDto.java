package com.homates.shoppinglist.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingListDto {
    private int idHouse;
    private String name;
    private List<ProductInListDto> products;
}

package com.homates.userhouses.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HouseUsersDto {
    private int id;
    private String name;
    private String description;
    private String address;
    private String owner;
    private List<String> roomMates = new ArrayList<>();
    //private List<UserDto> roomMates = new ArrayList<>();
}

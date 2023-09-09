package com.homates.userhouses.dto;

import com.homates.userhouses.model.UserEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HouseUsersDto {
    private String name;
    private String description;
    private String address;
    private UserDto owner;
    private List<UserDto> users = new ArrayList<>();
}

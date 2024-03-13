package com.homates.bacheca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HouseMessage implements Serializable {
    int idHouse;
    String message;
    MessageType messageType;
}

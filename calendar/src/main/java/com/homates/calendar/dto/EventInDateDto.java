package com.homates.calendar.dto;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EventInDateDto {

    private int idHouse;
    private LocalDate date;
}

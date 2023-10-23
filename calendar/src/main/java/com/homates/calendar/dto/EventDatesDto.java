package com.homates.calendar.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDatesDto {

    //dto per cercare gli eventi un un range di date
    private LocalDate start;
    private LocalDate end;


}

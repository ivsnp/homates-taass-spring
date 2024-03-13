package com.homates.calendar.dto;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ChangeEventDto {

    private String description;
    private String user;
    private LocalDate start;
    private LocalDate end;
    private LocalTime time;
    private String repetition;
    private String color;
    private int frequency;
}

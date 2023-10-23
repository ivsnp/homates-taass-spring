package com.homates.calendar.dto;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDto {
//dto per creare un nuovo evento con tutte le sue informazioni

    private String description;
    private String user;
    private int idHouse;
    private LocalDate start;
    private LocalDate end;
    private LocalTime time;
    private String repetition;
    private String color;
}

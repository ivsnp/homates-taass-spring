package com.homates.bacheca.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnounceDto {

    private int idHouse;

    private String description;

    private String user;

    private LocalDate date;
}

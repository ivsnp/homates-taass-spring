package com.homates.bacheca.dto;

import lombok.Data;

@Data
public class AnnounceDtoEdit {

    private int idAnnounce;

    private String description;

    // business rule: only user can edit their announces and date cannot be changed in order to preserve transparency
    // private String user;
    // private LocalDate date;
}

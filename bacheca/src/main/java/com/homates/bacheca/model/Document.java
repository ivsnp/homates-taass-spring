package com.homates.bacheca.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
public class Document {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

        @Column(name = "category")
        private String category;

        @Column(name = "path")
        private String path;

        @Column(name = "contentType")
        private String contentType;

        @Column(name = "size")
        private Long size;

    }





package com.homates.bacheca.model;
import jakarta.persistence.*;

@Entity
@Table(name = "document")

public class Document {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "name")
        private String name;

        @Column(name = "category")
        private String category;  //if pdf, image or file.

        public Document() {
        }

        public Document(String name, String category) {
            this.name = name;
            this.category = category;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "Document{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", category='" + category + '\'' +
                    '}';
        }

    }





package com.homates.bacheca.model;
import jakarta.persistence.*;

@Entity
@Table(name = "document")

public class Document {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;


        @Column(name = "category")
        private String category;

        @Column(name = "path")
        private String path;

        @Column(name = "contentType")
        private String contentType;

        @Column(name = "size")
        private Long size;


        public Document() {
        }

        public Document(String category,String path, String contentType, Long size) {
            this.category = category;
            this.path = path;
            this.contentType = contentType;
            this.size = size;
        }

        public long getId() {
            return id;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public void setId(long id) {
                this.id = id;
            }


        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPath() {
        return path;
    }

        public void setPath(String path) {
        this.path = path;
    }

        @Override
        public String toString() {
            return "Document{" +
                    "id=" + id +
                    ", category='" + category + '\'' +
                    ", path='" + path + '\'' +
                    '}';
        }

    }





package com.homates.bacheca.model;
import jakarta.persistence.*;

@Entity
@Table(name = "announce")
public class Announce {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "description")
        private String description;

        @Column(name="username")
        private String user;

        @Column(name="date")
        private String date;


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }


        public Announce() {
        }

        public Announce(String description, String user, String date) {
            this.description = description;
            this.user = user;
            this.date=date;
        }

        @Override
        public String toString() {
            return "Announce{" +
                    "id=" + id +
                    ", description='" + description + '\'' +
                    ", user='" + user + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }
    }



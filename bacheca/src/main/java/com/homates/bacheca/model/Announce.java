package com.homates.bacheca.model;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "announce")
public class Announce{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "description")
        private String description;

        @Column(name="username")
        private String user;

        @Column(name="date")
        private String date;


        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name="documents")
        private List<Document> documents; //inspection 'persistent attribute type checks' options

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public List<Document> getDocuments() {
            return documents;
        }

        public void setDocuments(List<Document> documents) {
            this.documents = documents;
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

        public void addDocument(Document d){
            documents.add(d);
        }

        public void removeDocument(Document d){
            documents.remove(d);
        }
        public Announce() {
        }

        public Announce(String description, String user, String date, List<Document> documents) {
            this.description = description;
            this.user = user;
            this.date=date;
            this.documents = new ArrayList<Document>();
        }

    public Document findDocumentByID(long id) {

        for(int i = 0; i < documents.size(); i++){
            Document doc = documents.get(i);
            if(doc.getId() == id){
                return doc;
            }
        }
        return null;
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



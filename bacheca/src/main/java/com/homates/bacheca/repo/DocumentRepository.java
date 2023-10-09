package com.homates.bacheca.repo;


import com.homates.bacheca.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;


public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findById(int id);
    List<Document> findByCategory(String category);
}

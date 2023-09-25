package com.homates.bacheca.repo;


import com.homates.bacheca.model.Document;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface DocumentRepository extends CrudRepository<Document, Long>{
    List<Document> findByCategory(String category);
}

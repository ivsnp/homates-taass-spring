package com.homates.shoppinglist.repo;

import com.homates.shoppinglist.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(int id);
    List<Product> findByName(String name);
}

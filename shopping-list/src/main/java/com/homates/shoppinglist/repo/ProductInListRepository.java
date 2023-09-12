package com.homates.shoppinglist.repo;

import com.homates.shoppinglist.model.ProductInList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductInListRepository extends JpaRepository<ProductInList, Long> {
    Optional<ProductInList> findById(int id);
}

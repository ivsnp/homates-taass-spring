package com.homates.listespesa.repo;

import com.homates.listespesa.model.Product;
import com.homates.listespesa.model.ShoppingList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<ShoppingList, Long> {
    List<Product> findByName(String name);
}

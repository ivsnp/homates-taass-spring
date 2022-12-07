package com.homates.listespesa.repo;

import com.homates.listespesa.model.ShoppingList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {
    List<ShoppingList> findByName(String name);
}

package com.homates.shoppinglist.repo;

import com.homates.shoppinglist.model.ShoppingList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {
    List<ShoppingList> findByName(String name);
}

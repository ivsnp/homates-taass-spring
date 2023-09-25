package com.homates.shoppinglist.repo;

import com.homates.shoppinglist.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Integer> {
    Optional<ShoppingList> findById(int id);
    List<ShoppingList> findByName(String name);
    List<ShoppingList> findByIdHouse(int idHouse);
}

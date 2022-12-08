package com.homates.listespesa.controller;

import com.homates.listespesa.model.ShoppingList;
import com.homates.listespesa.repo.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ShoppingListController {

    @Autowired
    ShoppingListRepository repository;

    @GetMapping("/shopping-lists")
    public List<ShoppingList> getAllCustomers() {
        System.out.println("Get all shopping lists...");

        List<ShoppingList> shoppingList = new ArrayList<>();
        repository.findAll().forEach(shoppingList::add);

        return shoppingList;
    }

    @PostMapping(value = "/shopping-lists/create")
    public ShoppingList postCustomer(@RequestBody ShoppingList shoppingList) {
        ShoppingList _shoppingList = repository.save(new ShoppingList(shoppingList.getName(), shoppingList.getProductList()));
        return _shoppingList;
    }

    @DeleteMapping("/shopping-lists/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
        System.out.println("Shopping list with ID = " + id + "...");

        repository.deleteById(id);

        return new ResponseEntity<>("Shopping list has been deleted!", HttpStatus.OK);
    }

    @DeleteMapping("/shopping-lists/delete")
    public ResponseEntity<String> deleteAllCustomers() {
        System.out.println("Delete shopping lists...");

        repository.deleteAll();

        return new ResponseEntity<>("All shopping lists have been deleted!", HttpStatus.OK);
    }

    @GetMapping(value = "/shopping-lists/name/{name}")
    public List<ShoppingList> findByName(@PathVariable String name) {

        List<ShoppingList> _shoppingList = repository.findByName(name);
        return _shoppingList;
    }

    @PutMapping("/shopping-lists/update-name/{id}/{name}")
    public ResponseEntity<ShoppingList> updateCustomer(@PathVariable("id") long id, @PathVariable("name") String name) {
        System.out.println("Update shopping list name with ID = " + id + "...");

        Optional<ShoppingList> shoppingList = repository.findById(id);

        if (shoppingList.isPresent()) {
            ShoppingList _shopL = shoppingList.get();
            _shopL.setName(name);
            return new ResponseEntity<>(repository.save(_shopL), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // TODO: add and remove items from list by ID
}

package com.homates.listespesa.controller;

import com.homates.listespesa.model.ShoppingList;
import com.homates.listespesa.repo.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
}

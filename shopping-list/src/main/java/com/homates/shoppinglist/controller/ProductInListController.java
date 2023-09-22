package com.homates.shoppinglist.controller;

import com.homates.shoppinglist.repo.ProductInListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/shopping-in-list")
public class ProductInListController {
    @Autowired
    ProductInListRepository repository;
}

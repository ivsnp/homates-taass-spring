package com.homates.shoppinglist.controller;

import com.homates.shoppinglist.repo.ProductInListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://shopping-list:4200")
@RestController
@RequestMapping("/api/v1/shopping-list")
public class ProductInListController {
    @Autowired
    ProductInListRepository repository;
}

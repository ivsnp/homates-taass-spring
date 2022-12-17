package com.homates.listespesa.controller;

import com.homates.listespesa.model.Product;
import com.homates.listespesa.repo.ProductRepository;
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
public class ProductController {
    @Autowired
    ProductRepository repository;

    @PostMapping(value = "/products/create")
    public Product addItem(@RequestBody Product product) {
        System.out.println("Creating new product...");
        return repository.save(product);
    }

    @GetMapping("/products")
    public List<Product> getItems() {
        System.out.println("Get all products...");
        List<Product> productList = new ArrayList<>();
        repository.findAll().forEach(productList::add);
        return productList;
    }

    @PutMapping(value = "/products/update/{id}")
    public ResponseEntity<Product> updateItem(@PathVariable("id") long id, @RequestBody Product newProduct) {
        System.out.println("Updating product...");

        Optional<Product> product = repository.findById(id);
        if (product.isPresent()) {
            Product _currentProduct = product.get();
            _currentProduct.setName(newProduct.getName());
            _currentProduct.setCategory(newProduct.getCategory());
            return new ResponseEntity<>(repository.save(_currentProduct), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/products/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") long id) {
        System.out.println("Deleting product...");

        Optional<Product> product = repository.findById(id);
        if (product.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>("Product has been deleted!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

package com.homates.shoppinglist.controller;

import com.homates.shoppinglist.dto.ProductDto;
import com.homates.shoppinglist.model.Product;
import com.homates.shoppinglist.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://shopping-list:4200")
@RestController
@RequestMapping("/api/v1/shoppinglist")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping(value = "/products/create")
    public ResponseEntity<String> addItem(@RequestBody ProductDto productDto) {
        System.out.println("Creating new product...");

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());

        productRepository.save(product);
        return new ResponseEntity<>("Product added.", HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getItems() {
        System.out.println("Getting products...");

        List<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productList::add);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping(value = "/products/update/{id}")
    public ResponseEntity<String> updateItem(@PathVariable("id") long id,
                                             @RequestBody ProductDto productDto) {
        System.out.println("Updating product...");

        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
            return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);

        Product _currentProduct = product.get();
        _currentProduct.setName(productDto.getName());
        _currentProduct.setCategory(productDto.getCategory());
        productRepository.save(_currentProduct);
        return new ResponseEntity<>("Product updated.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") long id) {
        System.out.println("Deleting product...");

        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
            return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);

        productRepository.delete(product.get());
        return new ResponseEntity<>("Product has been deleted.", HttpStatus.OK);
    }
}

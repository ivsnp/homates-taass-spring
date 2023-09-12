package com.homates.shoppinglist.controller;

import com.homates.shoppinglist.dto.ProductInListDto;
import com.homates.shoppinglist.dto.ShoppingListDto;
import com.homates.shoppinglist.dto.ShoppingListMetadataDto;
import com.homates.shoppinglist.model.Product;
import com.homates.shoppinglist.model.ProductInList;
import com.homates.shoppinglist.model.ShoppingList;
import com.homates.shoppinglist.repo.ProductInListRepository;
import com.homates.shoppinglist.repo.ProductRepository;
import com.homates.shoppinglist.repo.ShoppingListRepository;
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
public class ShoppingListController {

    @Autowired
    ShoppingListRepository shoppingListRepository;

    @Autowired
    ProductInListRepository productInListRepository;

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/create")
    public ResponseEntity<String> addItem(@RequestBody ShoppingListDto shoppingListDto) {
        System.out.println("Creating a new shopping list...");

        ShoppingList _currentShoppingList = new ShoppingList();
        _currentShoppingList.setName(shoppingListDto.getName());
        _currentShoppingList.setIdHouse(shoppingListDto.getIdHouse());

        List<ProductInList> _currentProductsInList = new ArrayList<>();
        for (ProductInListDto productInListDto: shoppingListDto.getProducts()){
            Optional<Product> _currentProduct = productRepository.findById(productInListDto.getIdProduct());
            Product product;

            if (_currentProduct.isPresent()){
                product = _currentProduct.get();
                ProductInList productInList = new ProductInList();
                productInList.setProduct(product);
                productInList.setDescription(productInListDto.getDescription());
                productInListRepository.save(productInList);
                _currentProductsInList.add(productInList);
            }
        }

        _currentShoppingList.setProductList(_currentProductsInList);

        shoppingListRepository.save(_currentShoppingList);

        return new ResponseEntity<>("Shopping list added.", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ShoppingList> getItem(@PathVariable("id") int id) {
        System.out.println("Gettig shopping list "+id+" ...");
        ShoppingList _currentShoppingList = new ShoppingList();

        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        if (shoppingList.isEmpty())
            return new ResponseEntity<>(_currentShoppingList, HttpStatus.NOT_FOUND);
        _currentShoppingList = shoppingList.get();
        return new ResponseEntity<>(_currentShoppingList, HttpStatus.OK);
    }

    @GetMapping(value = "/house/{id}")
    public ResponseEntity<List<ShoppingList>> getItems(@PathVariable("id") int id) {
        System.out.println("Gettig shopping list "+id+" ...");
        List<ShoppingList> _currentShoppingLists = new ArrayList<>();

        List<ShoppingList> shoppingList = shoppingListRepository.findByIdHouse(id);
        if (shoppingList.isEmpty())
            return new ResponseEntity<>(_currentShoppingLists, HttpStatus.NOT_FOUND);
        _currentShoppingLists = shoppingList;
        return new ResponseEntity<>(_currentShoppingLists, HttpStatus.OK);
    }

    @PutMapping(value = "/update-metadata/{id}")
    public ResponseEntity<String> updateItem(@PathVariable("id") int id,
                                             @RequestBody ShoppingListMetadataDto shoppingListMetadataDto) {
        System.out.println("Updating shopping list "+id+" ...");

        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        if (shoppingList.isEmpty())
            return new ResponseEntity<>("Shopping list not found.", HttpStatus.NOT_FOUND);

        ShoppingList _currentShoppingList = shoppingList.get();
        _currentShoppingList.setName(shoppingListMetadataDto.getName());
        _currentShoppingList.setIdHouse(shoppingListMetadataDto.getIdHouse());
        shoppingListRepository.save(_currentShoppingList);
        return new ResponseEntity<>("Shopping list updated.", HttpStatus.OK);
    }

    @PutMapping(value = "/update-productlist-description/{id}")
    public ResponseEntity<String> updateProductDescription(@PathVariable("id") int id,
                                                           @RequestBody String description) {
        System.out.println("Updating ProductInList description "+id+" ...");

        Optional<ProductInList> productInList = productInListRepository.findById(id);
        if (productInList.isEmpty())
            return new ResponseEntity<>("ProductInList not found.", HttpStatus.NOT_FOUND);

        ProductInList _currentProductInList = productInList.get();
        _currentProductInList.setDescription(description);
        productInListRepository.save(_currentProductInList);
        return new ResponseEntity<>("ProductInList updated.", HttpStatus.OK);
    }

    @PostMapping(value = "/add-product/{idHouse}")
    public ResponseEntity<String> addProduct(@PathVariable("idHouse") int id,
                                             @RequestBody ProductInListDto productInListDto) {
        System.out.println("Adding product to shopping list "+id+" ...");

        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        if (shoppingList.isEmpty())
            return new ResponseEntity<>("Shopping list not found.", HttpStatus.NOT_FOUND);

        Optional<Product> _currentProduct = productRepository.findById(productInListDto.getIdProduct());
        if (_currentProduct.isEmpty())
            return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);

        ShoppingList _currentShoppingList = shoppingList.get();
        Product product = _currentProduct.get();

        ProductInList productInList = new ProductInList();
        productInList.setProduct(product);
        productInList.setDescription(productInList.getDescription());
        _currentShoppingList.getProductList().add(productInList);
        shoppingListRepository.save(_currentShoppingList);
        return new ResponseEntity<>("Shopping list updated.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove-product/{idHouse}")
    public ResponseEntity<String> removeProduct(@PathVariable("idHouse") int id,
                                             @RequestBody ProductInListDto productInListDto) {
        System.out.println("Removing product to shopping list "+id+" ...");

        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        if (shoppingList.isEmpty())
            return new ResponseEntity<>("Shopping list not found.", HttpStatus.NOT_FOUND);

        Optional<Product> _currentProduct = productRepository.findById(productInListDto.getIdProduct());
        if (_currentProduct.isEmpty())
            return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);

        ShoppingList _currentShoppingList = shoppingList.get();
        Product product = _currentProduct.get();

        ProductInList productInList = new ProductInList();
        productInList.setProduct(product);
        productInList.setDescription(productInList.getDescription());
        _currentShoppingList.getProductList().remove(productInList);
        shoppingListRepository.save(_currentShoppingList);
        return new ResponseEntity<>("Shopping list updated.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int id) {
        System.out.println("Deleting shopping list "+id+" ...");

        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        if (shoppingList.isEmpty())
            return new ResponseEntity<>("Shopping list not found.", HttpStatus.NOT_FOUND);

        ShoppingList _currentShoppingList = shoppingList.get();
        shoppingListRepository.delete(_currentShoppingList);
        return new ResponseEntity<>("Shopping list has been deleted.", HttpStatus.OK);
    }
}

package com.homates.listespesa.controller;

import com.homates.listespesa.model.Product;
import com.homates.listespesa.model.ProductInList;
import com.homates.listespesa.model.ShoppingList;
import com.homates.listespesa.repo.ProductInListRepository;
import com.homates.listespesa.repo.ProductRepository;
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

    @Autowired
    ProductInListRepository repositoryProductInList;

    @Autowired
    ProductRepository repositoryProduct;

    @PostMapping(value = "/shopping-lists/create")
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody ShoppingList shoppingList) {
        List<ProductInList> _productInList = new ArrayList<>();
        // TODO manage transaction, if one fail delete everything
        for(int i = 0; i < shoppingList.getProductList().size(); i++){
            Product requestProduct = shoppingList.getProductList().get(i).getProduct();

            if(requestProduct.getId() == -1) // NOTE: if new prod, set id = -1
                requestProduct = repositoryProduct.save(new Product(requestProduct.getName().toLowerCase(), requestProduct.getCategory().toLowerCase()));

            Optional<Product> product = repositoryProduct.findById(requestProduct.getId());
            if (product.isPresent()) // create match between shopping-list and products
                _productInList.add(repositoryProductInList.save(new ProductInList(product.get(), shoppingList.getProductList().get(i).getDescr())));
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(repository.save(new ShoppingList(shoppingList.getName(), _productInList)), HttpStatus.OK);
    }

    @GetMapping("/shopping-lists")
    public List<ShoppingList> getAllShoppingLists() {
        System.out.println("Get all shopping lists...");

        List<ShoppingList> shoppingList = new ArrayList<>();
        repository.findAll().forEach(shoppingList::add);

        return shoppingList;
    }

    @GetMapping(value = "/shopping-lists/name/{name}")
    public List<ShoppingList> getAllShoppingListsByName(@PathVariable("name") String name) {
        return repository.findByName(name);
    }

    @DeleteMapping("/shopping-lists/delete/{id}")
    public ResponseEntity<String> deleteShoppingList(@PathVariable("id") long id) {
        System.out.println("Shopping list with ID = " + id + "...");

        Optional<ShoppingList> shopL = repository.findById(id);
        if (shopL.isPresent()) {
            ShoppingList _shopL = shopL.get();
            for(int i = 0; i < _shopL.getProductList().size(); i++){
                ProductInList _prodInList = _shopL.getProductList().get(i);
                repositoryProductInList.deleteById(_prodInList.getId());
            }
            repository.deleteById(id);
            return new ResponseEntity<>("Shopping list has been deleted!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/shopping-list/add-item/{id_list}")
    public ResponseEntity<ShoppingList> addProdInShoppingList(@PathVariable("id_list") long id_list, @RequestBody ProductInList producInList) {
        System.out.println("Add Item in List with ID = " + id_list + "...");
        Optional<ShoppingList> shoppingList = repository.findById(id_list);

        // check if product exists, otherwise create product. It doesn't exists if id==-1, otherwise bad data
        Product requestProduct = producInList.getProduct();
        if(requestProduct.getId() == -1) { // NOTE: if new prod, set id = -1
            requestProduct = repositoryProduct.save(new Product(requestProduct.getName().toLowerCase(), requestProduct.getCategory().toLowerCase()));
            producInList.setProduct(requestProduct);
        }
        Optional<Product> product = repositoryProduct.findById(requestProduct.getId());
        if (product.isEmpty()) // create match between shopping-list and products
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ProductInList _productInList = repositoryProductInList.save(producInList);
        if (shoppingList.isPresent()) {
            ShoppingList _shopL = shoppingList.get();
            _shopL.getProductList().add(_productInList);
            return new ResponseEntity<>(repository.save(_shopL), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/shopping-list/delete-item/{id_list}/{id_prod}")
    public ResponseEntity<ShoppingList> deleteProdInShoppingList(@PathVariable("id_list") long id_list, @PathVariable("id_prod") long id_prod) {
        System.out.println("Delete Item with ID = " + id_prod + " in List with ID = " + id_list + "...");
        Optional<ShoppingList> shoppingList = repository.findById(id_list);
        if (shoppingList.isPresent()) {
            ShoppingList _shopL = shoppingList.get();
            ProductInList p = _shopL.findProductByID(id_prod);
            _shopL.getProductList().remove(p);
            return new ResponseEntity<>(repository.save(_shopL), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/shopping-lists/update-name/{id}/{name}")
    public ResponseEntity<ShoppingList> updateShoppingList(@PathVariable("id") long id, @PathVariable("name") String name){
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
}

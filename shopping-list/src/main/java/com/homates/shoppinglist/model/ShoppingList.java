package com.homates.shoppinglist.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "shoppinglist")
@Data
@NoArgsConstructor
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idHouse;

    private String name;

    @OneToMany
    @JoinColumn(name = "id_shopping_list")
    private List<ProductInList> productList;

    // TODO: use repositoryProductInList JPA, not this func
    public ProductInList findProductByID(long id) {
        ProductInList prod;
        for(int i = 0; i < productList.size(); i++){
            prod = productList.get(i);
            if(prod.getId() == id){
                return prod;
            }
        }
        return null;
    }
}

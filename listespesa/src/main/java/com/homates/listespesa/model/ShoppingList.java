package com.homates.listespesa.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shoppingList")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    // TODO: check OneToMany
    @OneToMany
    @JoinColumn(name = "productList", nullable = false)
    private List<ProducInList> productList;

    public ShoppingList(String name, List<ProducInList> productList) {
        this.name = name;
        this.productList = productList;
    }

    public ShoppingList() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProducInList> getProductList() {
        return productList;
    }

    public void setProductList(List<ProducInList> productList) {
        this.productList = productList;
    }

    public ProducInList findProductByID(long id) {
        ProducInList prod;
        for(int i = 0; i < productList.size(); i++){
            prod = productList.get(i);
            if(prod.getId() == id){
                return prod;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productList=" + productList +
                '}';
    }
}

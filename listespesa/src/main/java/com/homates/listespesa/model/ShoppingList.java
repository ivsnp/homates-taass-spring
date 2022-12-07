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
    private List<ProducIntList> productList;

    public ShoppingList(String name, List<ProducIntList> productList) {
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

    public List<ProducIntList> getProductList() {
        return productList;
    }

    public void setProductList(List<ProducIntList> productList) {
        this.productList = productList;
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

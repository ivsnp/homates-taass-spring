package com.homates.listespesa.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "productList")
public class ProductInList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product")
    private Product product;

    @Column(name = "descr")
    private String descr;

    public ProductInList() {
    }

    public ProductInList(Product product, String descr) {
        this.product = product;
        this.descr = descr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "id=" + id +
                ", product=" + product +
                ", descr=" + descr +
                '}';
    }
}

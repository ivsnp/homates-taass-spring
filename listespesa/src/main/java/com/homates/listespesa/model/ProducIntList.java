package com.homates.listespesa.model;

import javax.persistence.*;

@Entity
@Table(name = "productList")
public class ProducIntList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // TODO: check ManyToOne
    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @Column(name = "descr")
    private int descr;

    public ProducIntList() {
    }

    public ProducIntList(Product product, int descr) {
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

    public int getDescr() {
        return descr;
    }

    public void setDescr(int descr) {
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

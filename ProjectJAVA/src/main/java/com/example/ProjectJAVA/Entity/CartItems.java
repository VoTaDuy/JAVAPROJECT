package com.example.ProjectJAVA.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Carts carts;

    @Column(name = "quantity")
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Carts getCarts() {
        return carts;
    }

    public void setCarts(Carts carts) {
        this.carts = carts;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package com.example.ProjectJAVA.Entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Carts carts;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(name = "total_price")
    private BigDecimal totalPrice = BigDecimal.ZERO;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(quantity)) ;
    }

    public BigDecimal setUnitPrice(BigDecimal unitPrice) {
        return this.unitPrice = products.getPrice();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}

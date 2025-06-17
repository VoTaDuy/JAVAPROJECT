package com.example.ProjectJAVA.Entity;


import com.example.ProjectJAVA.Embeddable.OrderProductId;
import jakarta.persistence.*;

@Entity
@Table(name = "order_products")
public class OrderProduct {

    @EmbeddedId
    private OrderProductId orderProductId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Products products;

    public OrderProductId getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(OrderProductId orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}

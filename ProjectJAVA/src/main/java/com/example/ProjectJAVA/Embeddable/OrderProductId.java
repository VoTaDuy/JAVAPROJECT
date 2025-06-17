package com.example.ProjectJAVA.Embeddable;

import java.io.Serializable;
import java.util.Objects;

public class OrderProductId implements Serializable {

    private Integer orderId;
    private Integer productId;


    public OrderProductId() {
    }
    public OrderProductId(Integer orderId, Integer productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public Integer getOrder_id() {
        return orderId;
    }

    public void setOrder_id(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProduct_id() {
        return productId;
    }

    public void setProduct_id(Integer product_id) {
        this.productId = product_id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductId that = (OrderProductId) o;
        return Objects.equals(orderId, that.productId) &&
                Objects.equals(productId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

}

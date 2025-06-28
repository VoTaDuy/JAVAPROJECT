package com.example.ProjectJAVA.Payloads.Request;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class ProductRequest {

    private MultipartFile file;
    private String product_name;
    private String description;
    private int quantity;
    private BigDecimal price;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}

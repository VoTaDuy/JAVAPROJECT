package com.example.ProjectJAVA.Payloads.Request;

public class CategoryRequest {
    private String category_name;
    private String description;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

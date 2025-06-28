package com.example.ProjectJAVA.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ReviewDTO {
    private int id;
    private Integer userId;
    private Integer productId;
    private LocalDateTime ReviewDate;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public LocalDateTime getReviewDate() {
        return ReviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        ReviewDate = reviewDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

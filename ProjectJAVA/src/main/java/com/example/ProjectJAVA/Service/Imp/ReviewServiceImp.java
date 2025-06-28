package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.DTO.ReviewDTO;
import com.example.ProjectJAVA.Entity.Reviews;

import java.util.List;

public interface ReviewServiceImp {
    Reviews createReview(int productId, int userId, String review);

    List<ReviewDTO> getReviewByProductId(Integer productId);

}

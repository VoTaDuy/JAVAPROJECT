package com.example.ProjectJAVA.Service;


import com.example.ProjectJAVA.DTO.ReviewDTO;
import com.example.ProjectJAVA.Entity.Reviews;
import com.example.ProjectJAVA.Repository.ReviewRepository;
import com.example.ProjectJAVA.Service.Imp.ReviewServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService implements ReviewServiceImp {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;


    @Override
    public Reviews createReview(int productId, int userId, String Comment) {
        productService.getProductById(productId);
        userService.findUserById(userId);
        Reviews newReview = new Reviews();
        newReview.setProducts(productService.getProductById(productId));
        newReview.setUsers(userService.findUserById(userId));
        newReview.setReviewDate(LocalDateTime.now());
        newReview.setComment(Comment);
        reviewRepository.save(newReview);

        return newReview;
    }

    @Override
    public List<ReviewDTO> getReviewByProductId(Integer productId) {
        List<Reviews> reviewsList = reviewRepository.findAllByProducts_productId(productId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (Reviews reviews : reviewsList){
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(reviews.getId());
            reviewDTO.setReviewDate(reviews.getReviewDate());
            reviewDTO.setComment(reviews.getComment());
            reviewDTO.setUserId(reviews.getUsers().getId());
            reviewDTO.setProductId(reviews.getProducts().getProductId());
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Reviews> reviewsList = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (Reviews reviews : reviewsList){
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(reviews.getId());
            reviewDTO.setReviewDate(reviews.getReviewDate());
            reviewDTO.setComment(reviews.getComment());
            reviewDTO.setUserId(reviews.getUsers().getId());
            reviewDTO.setProductId(reviews.getProducts().getProductId());
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }


}

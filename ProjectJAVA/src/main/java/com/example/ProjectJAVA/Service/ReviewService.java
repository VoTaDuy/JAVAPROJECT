package com.example.ProjectJAVA.Service;


import com.example.ProjectJAVA.Entity.Reviews;
import com.example.ProjectJAVA.Repository.ReviewRepository;
import com.example.ProjectJAVA.Service.Imp.ReviewServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}

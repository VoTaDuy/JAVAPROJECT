package com.example.ProjectJAVA.Controller;

import com.example.ProjectJAVA.Entity.Reviews;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.ReviewServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewServiceImp reviewServiceImp;

    @GetMapping("/get/{productId}")
    public ResponseEntity<?> getReviewByProductId(@PathVariable Integer productId){
        ResponseData responseData = new ResponseData();
        try {
            return new ResponseEntity<>(reviewServiceImp.getReviewByProductId(productId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestParam Integer productId,
                                          @RequestParam Integer userId,
                                          @RequestParam String reviewText){
        ResponseData responseData = new ResponseData();

        try {
            Reviews reviews = reviewServiceImp.createReview(productId, userId, reviewText);
            responseData.setData(reviews);
            responseData.setDesc("Review created successfully!");
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

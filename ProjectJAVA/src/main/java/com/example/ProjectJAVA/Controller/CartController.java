package com.example.ProjectJAVA.Controller;


import com.example.ProjectJAVA.Entity.Carts;
import com.example.ProjectJAVA.Exception.ResourceNotFoundException;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@CrossOrigin("*")
@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    CartServiceImp cartServiceImp;

    @GetMapping("/get/{id}/my-cart")
    public ResponseEntity<?> getCart(@PathVariable Integer id){
        ResponseData responseData = new ResponseData();
        try {
            Carts carts = cartServiceImp.getCartById(id);
            responseData.setData(carts);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            responseData.setData("Cart not found!");
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}/my-cart")
    public ResponseEntity<?> ClearCart(@PathVariable Integer id){

        ResponseData responseData = new ResponseData();
        try {

            cartServiceImp.ClearCart(id);
            responseData.setData("Cart cleared!");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (ResourceNotFoundException e) {
            responseData.setData("Cart not found!");
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/cart/total-price")
    public ResponseEntity<?> getCartTotal(@PathVariable Integer id){
        ResponseData responseData = new ResponseData();
        try {
            BigDecimal totalAmount = cartServiceImp.getCartTotal(id);

            responseData.setData(totalAmount);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            responseData.setData("Cart not found!");
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData,HttpStatus.NOT_FOUND);
        }
    }
}

package com.example.ProjectJAVA.Controller;

import com.example.ProjectJAVA.Exception.ResourceNotFoundException;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.CartItemServiceImp;
import com.example.ProjectJAVA.Service.Imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {

    @Autowired
    CartItemServiceImp cartItemServiceImp;

    @Autowired
    CartServiceImp cartServiceImp;


    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestParam  Integer product_id,
                                         @RequestParam Integer quantity,
                                         @RequestParam (required = false) Integer cart_id){
        ResponseData responseData = new ResponseData();
        try {
            if (cart_id == null){
                cart_id = cartServiceImp.initializeNewCart();

            }
            cartItemServiceImp.addItemToCart(cart_id,product_id,quantity);
            responseData.setData("add item success");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            responseData.setData("add failed");
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);        }
    }


    @DeleteMapping("{id}/remove/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Integer id,
                                            @PathVariable Integer itemId){
        ResponseData responseData = new ResponseData();
        try {
            cartItemServiceImp.removeItemFromCart(id,itemId);
            responseData.setData("remove item success");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            responseData.setData("remove failed");
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cart/{id}/{itemId}/update")
    public ResponseEntity<?> updateCartItem(@PathVariable Integer id,
                                            @PathVariable Integer itemId,
                                            @RequestParam Integer quantity){
        ResponseData responseData = new ResponseData();
        try {

            cartItemServiceImp.updateQuantityInCart(id,itemId,quantity);
            responseData.setData("update item success");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            responseData.setData("update failed");
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }



}

package com.example.ProjectJAVA.Controller;

import com.example.ProjectJAVA.Entity.Carts;
import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Exception.ResourceNotFoundException;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Repository.UserRepository;
import com.example.ProjectJAVA.Service.Imp.CartItemServiceImp;
import com.example.ProjectJAVA.Service.Imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/cartItem")
public class CartItemController {

    @Autowired
    CartItemServiceImp cartItemServiceImp;

    @Autowired
    CartServiceImp cartServiceImp;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestParam Integer product_id,
                                         @RequestParam Integer quantity,
                                         @RequestParam(required = false) Integer cart_id,
                                         @RequestParam(required = false) Integer user_id) {
        ResponseData responseData = new ResponseData();

        try {
            Carts cart;

            // Ưu tiên theo cart_id
            if (cart_id != null) {
                cart = cartServiceImp.getCartById(cart_id);
            }
            // Nếu không có cart_id nhưng có user_id → tìm theo user
            else if (user_id != null) {
                try {
                    cart = cartServiceImp.getCartByUserId(user_id);
                } catch (RuntimeException e) {
                    // Nếu user chưa có cart → tạo mới
                    Users user = userRepository.findById(user_id)
                            .orElseThrow(() -> new RuntimeException("User không tồn tại: " + user_id));

                    cart = cartServiceImp.createCartForUser(user);
                }
            }
            // Nếu cả 2 đều null → lỗi
            else {
                throw new RuntimeException("Thiếu cart_id và user_id để xử lý giỏ hàng.");
            }

            // Thêm item vào giỏ
            cartItemServiceImp.addItemToCart(cart.getId(), product_id, quantity);

            // Trả về cart_id cho frontend lưu
            responseData.setData(cart.getId());
            responseData.setSuccess(true);
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            responseData.setSuccess(false);
            responseData.setData("add failed");
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            responseData.setSuccess(false);
            responseData.setData("add failed");
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
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

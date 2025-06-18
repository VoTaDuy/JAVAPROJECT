package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.Entity.CartItems;

public interface CartItemServiceImp {
    void addItemToCart(int cart_id, int product_id, int quantity);
    void removeItemFromCart(int cart_id, int product_id);
    void updateQuantityInCart(int cart_id, int product_id, int quantity);
    CartItems getCartItemById(int cart_id, int product_id);


}

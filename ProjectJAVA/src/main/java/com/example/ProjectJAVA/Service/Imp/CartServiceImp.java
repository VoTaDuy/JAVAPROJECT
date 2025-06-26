package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.Entity.Carts;
import com.example.ProjectJAVA.Entity.Users;

import java.math.BigDecimal;
import java.util.List;

public interface CartServiceImp {
    Carts getCartById(int id);

    void ClearCart(int id);

    BigDecimal getCartTotal(int id);

    Integer initializeNewCart();

    Carts getCartByUserId(int userId);

    Carts createCartForUser(Users users);

    public List<Carts> getAllCarts();

    Carts getOrCreateCart(Integer cartId, Integer userId);
}

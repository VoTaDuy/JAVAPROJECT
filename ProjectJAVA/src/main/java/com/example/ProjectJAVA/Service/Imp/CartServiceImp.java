package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.Entity.Carts;

import java.math.BigDecimal;

public interface CartServiceImp {
    Carts getCartById(int id);
    void ClearCart(int id);

    BigDecimal getCartTotal(int id);
    Integer initializeNewCart();
}

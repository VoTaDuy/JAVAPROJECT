package com.example.ProjectJAVA.Service;


import com.example.ProjectJAVA.Entity.CartItems;
import com.example.ProjectJAVA.Entity.Carts;
import com.example.ProjectJAVA.Repository.CartItemRepository;
import com.example.ProjectJAVA.Repository.CartRepository;
import com.example.ProjectJAVA.Service.Imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CartService implements CartServiceImp {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    private final AtomicInteger cartIdGenerator = new AtomicInteger(0);

    @Override
    public Carts getCartById(int id) {

        Carts carts = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Can not found cart with ID: " + id));
        BigDecimal totalAmount = carts.getTotalAmount();
        carts.setTotalAmount(totalAmount);

        return cartRepository.save(carts);

    }

    @Override
    public void ClearCart(int id) {
        Carts carts = getCartById(id);
        cartItemRepository.deleteAllByCartsId(id);
        carts.getCartItems().clear();
        cartRepository.deleteById(id);


    }

    @Override
    public BigDecimal getCartTotal(int id) {
        Carts carts = getCartById(id);

        return carts.getTotalAmount();
    }

    @Override
    public Integer initializeNewCart() {
        Carts newCart = new Carts();
        Integer newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);

        return cartRepository.save(newCart).getId();

    }
}

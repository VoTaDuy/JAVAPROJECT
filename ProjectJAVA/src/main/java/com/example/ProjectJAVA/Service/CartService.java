package com.example.ProjectJAVA.Service;


import com.example.ProjectJAVA.Entity.Carts;
import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Repository.CartItemRepository;
import com.example.ProjectJAVA.Repository.CartRepository;
import com.example.ProjectJAVA.Repository.UserRepository;
import com.example.ProjectJAVA.Service.Imp.CartServiceImp;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CartService implements CartServiceImp {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserRepository userRepository;

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
    @Transactional
    public void ClearCart(int id) {
        Carts carts = getCartById(id);
        cartItemRepository.deleteAllByCartsId(id);
        carts.getCartItems().clear();
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

    @Override
    public Carts getCartByUserId(int userId) {
        return cartRepository.findByUsers_id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));    }

    @Override
    public Carts createCartForUser(Users users) {
        if (users == null) {
            throw new IllegalArgumentException("User cannot be null when creating a cart.");
        }
        return cartRepository.findByUsers_id(users.getId())
                .orElseGet(() -> {
                    Carts cart = new Carts();
                    cart.setUsers(users);
                    cart.setTotalAmount(BigDecimal.ZERO);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public List<Carts> getAllCarts() {
        return cartRepository.findAll();
    }


    @Override
    public Carts getOrCreateCart(Integer cartId, Integer userId) {
        if (cartId != null) {
            return cartRepository.findById(cartId)
                    .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        }

        if (userId != null) {
            return cartRepository.findByUsers_id(userId)
                    .orElseGet(() -> {
                        Users user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));
                        return createCartForUser(user);
                    });
        }

        throw new RuntimeException("Cannot find or create cart without cart_id or user_id");
    }
}

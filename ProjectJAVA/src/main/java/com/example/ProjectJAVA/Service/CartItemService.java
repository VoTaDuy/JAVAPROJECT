package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.Entity.CartItems;
import com.example.ProjectJAVA.Entity.Carts;
import com.example.ProjectJAVA.Entity.Products;
import com.example.ProjectJAVA.Exception.ResourceNotFoundException;
import com.example.ProjectJAVA.Repository.CartItemRepository;
import com.example.ProjectJAVA.Repository.CartRepository;
import com.example.ProjectJAVA.Service.Imp.CartItemServiceImp;
import com.example.ProjectJAVA.Service.Imp.CartServiceImp;
import com.example.ProjectJAVA.Service.Imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;


@Service
public class CartItemService implements CartItemServiceImp {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductServiceImp productServiceImp;

    @Autowired
    CartServiceImp cartServiceImp;


    @Override
    public void addItemToCart(int cart_id, int product_id, int quantity) {
        Carts carts = cartServiceImp.getCartById(cart_id);
        Products products = productServiceImp.getProductById(product_id);
        // Initialize cart items if null
        if (carts.getCartItems() == null) {
            carts.setCartItems(new HashSet<>());
        }


        CartItems cartItems = carts.getCartItems()
                .stream()
                .filter(item -> item.getProducts().getProductId().equals(product_id))
                .findFirst().orElse(new CartItems());

        if (cartItems.getId() == null){
            cartItems.setCarts(carts);
            cartItems.setProducts(products);
            cartItems.setQuantity(quantity);
            cartItems.setUnitPrice(products.getPrice());
        }else {
            cartItems.setQuantity(cartItems.getQuantity() + quantity);
        }

        cartItems.setTotalPrice();

        carts.addItem(cartItems);
        cartItemRepository.save(cartItems);
        cartRepository.save(carts);

    }

    @Override
    public void removeItemFromCart(int cart_id, int product_id) {
         Carts carts = cartServiceImp.getCartById(cart_id);

         CartItems itemToRemove = getCartItemById(cart_id, product_id);
         carts.removeItem(itemToRemove);
         cartRepository.save(carts);
    }

    @Override
    public void updateQuantityInCart(int cart_id, int product_id, int quantity) {
        Carts carts = cartServiceImp.getCartById(cart_id);
        carts.getCartItems()
                .stream()
                .filter(item -> item.getProducts().getProductId().equals(product_id))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProducts().getPrice());
                    item.setTotalPrice();
                });

        BigDecimal totalAmount = carts.getCartItems()
                .stream()
                .map(CartItems::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        carts.setTotalAmount(totalAmount);
        cartRepository.save(carts);
    }

    @Override
    public CartItems getCartItemById(int cart_id, int product_id) {
        Carts carts = cartServiceImp.getCartById(cart_id);
        return carts.getCartItems()
                .stream()
                .filter(item -> item.getProducts().getProductId().equals(product_id))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Can not found item with ID"));
    }
}

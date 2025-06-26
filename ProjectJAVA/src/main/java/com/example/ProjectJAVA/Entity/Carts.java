package com.example.ProjectJAVA.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "added_date")
    private Timestamp addedDate;


    @Column(name = "total_amount")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    @JsonManagedReference
    private Users users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }



    @OneToMany(mappedBy = "carts", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<CartItems> cartItems;

    public Set<CartItems> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItems> cartItems) {
        this.cartItems = cartItems;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @PrePersist
    protected void onCreate() {
        this.addedDate = new Timestamp(System.currentTimeMillis());
    }

    //update totalAmount
    private void updateTotalAmount() {
        this.totalAmount = cartItems.stream()
                .map(cartItem -> {
                    BigDecimal itemPrice = cartItem.getUnitPrice();
                    return (itemPrice != null) ? itemPrice.multiply(new BigDecimal(cartItem.getQuantity())) : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //add item to carts
    public void addItem(CartItems cartItem) {
        cartItems.add(cartItem);
        cartItem.setCarts(this);
        updateTotalAmount();
    }
    //remove item from cart
    public void removeItem(CartItems cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCarts(null);
        updateTotalAmount();
    }

}

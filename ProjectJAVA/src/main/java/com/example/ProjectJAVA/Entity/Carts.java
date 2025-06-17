package com.example.ProjectJAVA.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "added_date")
    private String addedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<CartItems> getCartItemsList() {
        return cartItemsList;
    }

    public void setCartItemsList(List<CartItems> cartItemsList) {
        this.cartItemsList = cartItemsList;
    }

    @OneToMany(mappedBy = "carts")
    private List<CartItems> cartItemsList;




}

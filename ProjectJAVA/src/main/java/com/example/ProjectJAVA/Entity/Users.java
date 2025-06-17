package com.example.ProjectJAVA.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created_Date")
    private Date created_Date;

    @OneToMany(mappedBy = "users")
    private List<Orders> orders;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;

    @OneToMany(mappedBy = "users")
    private List<Carts> cartsList;

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<Carts> getCartsList() {
        return cartsList;
    }

    public void setCartsList(List<Carts> cartsList) {
        this.cartsList = cartsList;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated_Date() {
        return created_Date;
    }

    public void setCreated_Date(Date created_Date) {
        this.created_Date = created_Date;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}

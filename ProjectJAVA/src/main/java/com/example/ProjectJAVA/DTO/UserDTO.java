package com.example.ProjectJAVA.DTO;

import java.time.LocalDateTime;
import java.util.Date;

public class UserDTO {
    private long id;
    private String username;
    private long role_id;
    private String password;
    private LocalDateTime created_Dated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_Dated() {
        return created_Dated;
    }

    public void setCreated_Dated(LocalDateTime created_Dated) {
        this.created_Dated = created_Dated;
    }
}

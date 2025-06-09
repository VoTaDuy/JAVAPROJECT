package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.DTO.UserDTO;
import com.example.ProjectJAVA.Entity.Users;

import java.util.List;

public interface UserServiceImp {
    List<UserDTO> getAllUsers();
    Users findUserById(long id);

}
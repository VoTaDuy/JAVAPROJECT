package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.DTO.UserDTO;
import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Payloads.Request.RegisterRequest;

import java.util.List;

public interface LoginServiceImp  {
    List<UserDTO> getAllUser();
    public Users CheckLogin(String username, String password);

    public boolean RegisterUser(RegisterRequest registerRequest);

}

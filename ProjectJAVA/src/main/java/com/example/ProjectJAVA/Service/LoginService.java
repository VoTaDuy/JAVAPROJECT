package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.DTO.UserDTO;
import com.example.ProjectJAVA.Entity.Roles;
import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Payloads.Request.RegisterRequest;
import com.example.ProjectJAVA.Repository.UserRepository;
import com.example.ProjectJAVA.Service.Imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUser() {
        List<Users> usersList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Users users : usersList) {
            UserDTO userDTO = new UserDTO();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
    @Override
    public Users CheckLogin(String username, String password) {

        Users users = userRepository.findByUsername(username);
        if (users == null){
            System.out.println("User not found");
            return null;
        }
        System.out.println("Input password" + password);
        System.out.println("Stored password" + users.getPassword());
        if (users.getPassword().equals(password)){
            System.out.println("Login success");
            return users;
        }else {
            System.out.println("Invalid password");
            return null;
        }
    }

    @Override
    public boolean RegisterUser(RegisterRequest registerRequest) {

        if (userRepository.findByUsername(registerRequest.getUsername()) != null){
            System.out.println("user exist");
            return false;
        }

        Users users = new Users();
        users.setUsername(registerRequest.getUsername());
        users.setPassword(registerRequest.getPassword());
        Roles roles = new Roles();
        roles.setRole_id(registerRequest.getRole_id());
        users.setRoles(roles);

        try {
            System.out.println("Inserting user");
            userRepository.save(users);
            System.out.println("Inserted user");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
    @Override
    public Users checkAdminLogin(String username, String password) {
        Users user = CheckLogin(username, password);
        if (user != null && user.getRoles().getRolename().equalsIgnoreCase("ADMIN")) {
            return user;
        }
        System.out.println("User is not an admin");
        return null;
    }
}

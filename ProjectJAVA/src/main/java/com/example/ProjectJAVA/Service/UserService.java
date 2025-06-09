package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.DTO.UserDTO;
import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Repository.UserRepository;
import com.example.ProjectJAVA.Service.Imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceImp {


    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> usersList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Users users : usersList){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getUser_id());
            userDTO.setRole_id(users.getRoles().getRole_id());
            userDTO.setPassword(users.getPassword());
            userDTO.setUsername(users.getUsername());
            userDTOList.add(userDTO);
            System.out.println(users.getUsername());
        }
        return userDTOList;
    }

    @Override
    public Users findUserById(long id) {
        return userRepository.findById((int) id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

    }
}

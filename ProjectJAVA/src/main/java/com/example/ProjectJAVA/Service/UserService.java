package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.DTO.UserDTO;
import com.example.ProjectJAVA.Entity.Carts;
import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Repository.CartRepository;
import com.example.ProjectJAVA.Repository.UserRepository;
import com.example.ProjectJAVA.Service.Imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceImp {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> usersList = userRepository.findAllByIsDeletedFalse();;
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Users users : usersList){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
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

    @Override
    public Users removeUserById(Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        user.setDeleted(true);
        userRepository.save(user);
        return user;
    }


}

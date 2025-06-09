package com.example.ProjectJAVA.Security;

import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        System.out.println("hhh");
        Users users = userRepository.findByUsername(username);
        if (users == null){
            throw new UsernameNotFoundException("User Not Found!");
        }
        return new User(username, users.getPassword(), new ArrayList<>());
    }
}

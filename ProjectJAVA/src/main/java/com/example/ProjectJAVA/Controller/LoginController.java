package com.example.ProjectJAVA.Controller;

import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Payloads.Request.RegisterRequest;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.LoginServiceImp;
import com.example.ProjectJAVA.Utils.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    JwtUtilHelper jwtUtilHelper;

    @Autowired
    LoginServiceImp loginServiceImp;
    @PostMapping("/sign_in")
    public ResponseEntity<?> Signin(@RequestParam String username, @RequestParam String password){
        System.out.println("username: " + username) ;
        System.out.println("password " + password);
        Users users = loginServiceImp.CheckLogin(username,password);
        ResponseData responseData = new ResponseData();
        if (users != null){
            String token = jwtUtilHelper.generateToken(users.getPassword());
            Map<String,Object> data = new HashMap<>();
            data.put("userId" , users.getUser_id());
            data.put("username", users.getUsername());
            data.put("token", token);
            responseData.setData(data);
            responseData.setSuccess(true);
        }else {
            responseData.setData("username or password is not correct");
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/Sign_up")
    public ResponseEntity<?> Signup(@RequestBody RegisterRequest registerRequest){
        ResponseData responseData = new ResponseData();
        Boolean isRegisterSuccess = loginServiceImp.RegisterUser(registerRequest);
        if (isRegisterSuccess == true){
            responseData.setSuccess(true);
            responseData.setData("sign up successful");
        }else{
            responseData.setSuccess(false);
            responseData.setData("Sign up fail! please try again and check your information!");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    }


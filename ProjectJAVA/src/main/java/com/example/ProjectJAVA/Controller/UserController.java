package com.example.ProjectJAVA.Controller;


import com.example.ProjectJAVA.Entity.Users;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.UserServiceImp;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userServiceImp.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/crsf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @DeleteMapping("/{userId}/delete_user")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        ResponseData reponseData = new ResponseData();
        try {
            Users users=userServiceImp.removeUserById(userId);
            reponseData.setData(users);
            return new ResponseEntity<>(reponseData, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(reponseData, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(reponseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

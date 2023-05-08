package com.gocarshare.userservice.userservice.Controller;


import com.gocarshare.userservice.userservice.Modal.User;
import com.gocarshare.userservice.userservice.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<User> getUserDetails(Authentication auth){
        User user = (User) auth.getPrincipal();
        return  new ResponseEntity<>(userService.getUserDetails(user.get_id()), HttpStatus.OK);
    }

    @PostMapping("/updateType")
    public ResponseEntity<?> updateUserType(@RequestParam String userType, Authentication auth){
        User user = (User) auth.getPrincipal();
        return  new ResponseEntity<>(userService.setUserType(user.get_id(), userType), HttpStatus.OK);
    }
}

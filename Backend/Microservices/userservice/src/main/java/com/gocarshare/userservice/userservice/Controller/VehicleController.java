package com.gocarshare.userservice.userservice.Controller;

import com.gocarshare.userservice.userservice.Modal.User;
import com.gocarshare.userservice.userservice.Modal.UserVehicle;
import com.gocarshare.userservice.userservice.Services.UserService;
import com.gocarshare.userservice.userservice.Services.VehicleRegistrationService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired private VehicleRegistrationService vehicleRegistrationService;

    @Autowired private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> RegisterVehicle(@RequestBody UserVehicle userVehicle, Authentication authentication){

        User user =(User) authentication.getPrincipal();
        String userId = user.get_id();

        return new ResponseEntity<>(vehicleRegistrationService.registerVehicle(userId, userVehicle), org.springframework.http.HttpStatus.valueOf(HttpStatus.SC_OK));

    }

    @GetMapping("/vehicle")
    public Object getUserVehicles(Authentication authentication){
        User user =(User) authentication.getPrincipal();
        String userId = user.get_id();

        return  vehicleRegistrationService.getUserVehicles(userId);
    }

    @GetMapping("/test")
    public String test(){
        return "This thing is working";
    }
}

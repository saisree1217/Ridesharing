package com.gocarshare.authserver.Controllers;

import com.gocarshare.authserver.Exception.ExceptionResponse;
import com.gocarshare.authserver.Modal.SignUpForm;
import com.gocarshare.authserver.Modal.User;
import com.gocarshare.authserver.Service.SignUpService;
import com.gocarshare.authserver.Service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private UserService userService;

    @Autowired private SignUpService signUpService;

    @PostMapping("/signUp")
    public ResponseEntity<?> singUp(@RequestBody SignUpForm form)
            throws ExecutionException, FirebaseAuthException, InterruptedException {

        return new ResponseEntity<>(signUpService.signup(form.getEmailId(), form.getPassword(), form.getDisplayName()), HttpStatus.OK);

    }

    @GetMapping("/signIn")
    public Object singIn(@RequestParam String emailId,
                         @RequestParam String password) throws URISyntaxException, IOException {

        return signUpService.signIn(emailId, password);

    }

    @GetMapping("/test")
    public Object test(){
        User u = new User();
        u.setEmail("Sample mail");
        u.setDisplayName("I'm Working here");
        return u;
    }

}

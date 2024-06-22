package com.airbnb.controller;

import com.airbnb.dto.LogInDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.dto.TokenResponse;
import com.airbnb.entity.PropertyUser;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController { //45.00-sign in  octt-27/03


    private UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody  PropertyUserDto propertyUserDto){
        PropertyUser propertyUser = userService.addUsers(propertyUserDto);
        if (propertyUser!=null){
            return new ResponseEntity<>("Registration is Successful..", HttpStatus.CREATED);
        }
        return  new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LogInDto logInDto){ //1.sending dto to logon
        String token = userService.verifyLogIn(logInDto);//2. login calling verifyLogin method
       if (token!=null){
           TokenResponse tokenResponse = new TokenResponse();
           tokenResponse.setToken(token);
           return new ResponseEntity<>(tokenResponse,HttpStatus.OK); //6.return token  back-> now that token is use further url protection
       }
        return new ResponseEntity<>(" Invalid credential",HttpStatus.UNAUTHORIZED);
    }

@GetMapping("/profile") //when i access this url automatically extract the session id & check session id with spring boot
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user){//which it has generated earlier if that matches
        return user;//which means it is current user interacting and that user details automatically put that ito PropertyUser user

    }
}

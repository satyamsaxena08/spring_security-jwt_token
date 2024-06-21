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
    public ResponseEntity<?> logIn(@RequestBody LogInDto logInDto){
        String token = userService.verifyLogIn(logInDto);
       if (token!=null){
           TokenResponse tokenReaponse = new TokenResponse();
           tokenReaponse.setToken(token);
           return new ResponseEntity<>(tokenReaponse,HttpStatus.OK);
       }
        return new ResponseEntity<>(" Invalid credential",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/profile")
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user){
        return user;

    }
}

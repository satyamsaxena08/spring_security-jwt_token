package com.airbnb.service.impl;

import com.airbnb.dto.LogInDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private PropertyUserRepository propertyUserRepository;

    private JWTServiceImpl jwtService;

    public UserServiceImpl(PropertyUserRepository propertyUserRepository, JWTServiceImpl jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService = jwtService;
    }



    @Override
    public PropertyUser addUsers(PropertyUserDto propertyUserDto) {
        PropertyUser user = new PropertyUser();

        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail( propertyUserDto.getEmail());
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));
        user.setUserRole(propertyUserDto.getUserRole());
        PropertyUser savedUser = propertyUserRepository.save(user);
        return savedUser;
    }

    @Override
    public String verifyLogIn(LogInDto logInDto) {
        Optional<PropertyUser> byUsername = propertyUserRepository.findByUsername(logInDto.getUsername());
        if (byUsername.isPresent()){
            PropertyUser propertyUser = byUsername.get();
           if (BCrypt.checkpw(logInDto.getPassword(),propertyUser.getPassword())){
            return    jwtService.generateTokken(propertyUser);
           }
        }
        return null;
    }
}

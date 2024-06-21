package com.airbnb.service;

import com.airbnb.dto.LogInDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;

public interface UserService {

    public PropertyUser addUsers(PropertyUserDto propertyUserDto);

    String verifyLogIn(LogInDto logInDto);
}

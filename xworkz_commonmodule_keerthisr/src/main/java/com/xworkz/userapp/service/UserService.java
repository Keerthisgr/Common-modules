package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;

import java.lang.reflect.InvocationTargetException;

public interface UserService {
    boolean  validateAndUser(UserDto dto) throws InvocationTargetException, IllegalAccessException;
}

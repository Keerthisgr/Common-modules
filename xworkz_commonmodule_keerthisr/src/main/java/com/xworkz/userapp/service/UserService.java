package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;

public interface UserService {
    boolean  validateAndUser(UserDto dto, Model model) throws InvocationTargetException, IllegalAccessException;
}

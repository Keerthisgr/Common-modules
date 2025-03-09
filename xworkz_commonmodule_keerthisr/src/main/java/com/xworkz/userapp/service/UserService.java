package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;

public interface UserService {
    boolean  validateAndUser(UserDto dto, Model model) throws InvocationTargetException, IllegalAccessException;
    UserDto getPasswordByEmail(String email, String enteredPassword);
    String encryptPassword(String password);
    boolean matchPassword(String enteredPassword, String storedHash);
    boolean updateUserByEmail(String email, UserDto dto, Model model);
    UserDto getUserByEmail(String email);
}
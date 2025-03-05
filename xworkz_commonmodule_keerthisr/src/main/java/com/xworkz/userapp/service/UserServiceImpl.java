package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.entity.UserEntity;
import com.xworkz.userapp.repository.UserRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Override
    public boolean validateAndUser(UserDto dto, Model model) throws InvocationTargetException, IllegalAccessException {
        if (dto == null) {
            model.addAttribute("error", "User details cannot be null.");
            return false;
        }

        // Name Validation
        String nameRegex = "^[A-Z][a-zA-Z]{2,49}$";
        Pattern namePattern = Pattern.compile(nameRegex);

        if (dto.getName() == null || !namePattern.matcher(dto.getName()).matches()) {
            model.addAttribute("error", "Invalid Name");
            return false;
        }

        // Phone Number Validation
        String phoneRegex = "^[9876]\\d{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);

        if (dto.getPhoneNumber() == null || !phonePattern.matcher(String.valueOf(dto.getPhoneNumber())).matches()) {
            model.addAttribute("error", "Invalid Phone Number");
            return false;
        }

        // Password Matching Validation
        if (dto.getPassword() == null || dto.getConfirmPassword() == null ||
                !dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("error", "Password and Confirm Password must be the same.");
            return false;
        }

        // Convert DTO to Entity
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity); // Correct order

        // Save entity to database
        repository.saveUser(entity);
        return true;
    }
    }



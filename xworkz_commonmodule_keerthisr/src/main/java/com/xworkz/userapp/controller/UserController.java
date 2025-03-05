package com.xworkz.userapp.controller;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;


@Component
@RequestMapping("/")
public class UserController {

    public UserController() {
        System.out.println("controller default constructor is invoked");
    }

    @Autowired
    UserService userService;

    @RequestMapping("addUser")
    public String addUser(UserDto dto, Model model) throws InvocationTargetException, IllegalAccessException {

        String nameRegex = "^[A-Z][a-zA-Z]{2,49}$";
        Pattern namePattern = Pattern.compile(nameRegex);

        if (dto.getName() == null || !namePattern.matcher(dto.getName()).matches()) {
            model.addAttribute("error", "Invalid Name");
            return "error.jsp";
        }

        String phoneRegex = "^[9876]\\d{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);

        if (dto.getPhoneNumber() == null || !phonePattern.matcher(String.valueOf(dto.getPhoneNumber())).matches()) {
            model.addAttribute("error", "Invalid Phone Number");
            return "error.jsp";
        }

        if (dto.getPassword() == null || dto.getConfirmPassword() == null ||
                !dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("error", "Password and Confirm Password must be the same.");
            return "error.jsp";
        }

        userService.validateAndUser(dto);
        model.addAttribute("name", dto.getName());
        return "response.jsp";
    }
}

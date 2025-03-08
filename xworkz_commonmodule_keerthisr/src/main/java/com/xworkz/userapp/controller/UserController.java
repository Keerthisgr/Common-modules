package com.xworkz.userapp.controller;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;

@Component
@RequestMapping("/")
public class UserController {

    public UserController() {
        System.out.println("Controller default constructor is invoked");
    }

    @Autowired
    UserService userService;

    @RequestMapping("addUser")
    public String addUser(UserDto dto, Model model) throws InvocationTargetException, IllegalAccessException {

        // Validate password and confirm password
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("error", "Password and Confirm Password must match.");
            return "error.jsp";
        }

        // Encrypt and save user
        dto.setPassword(userService.encryptPassword(dto.getPassword()));
        dto.setConfirmPassword(dto.getPassword());

        boolean isValid = userService.validateAndUser(dto, model);

        if (!isValid) {
            return "error.jsp";
        }

        model.addAttribute("name", dto.getName());
        return "response.jsp";
    }

    @PostMapping("/signIn")
    public String signIn(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            UserDto user = userService.getPasswordByEmail(email,password);

            if (user == null) {
                model.addAttribute("error", "User not found");
                return "error.jsp";
            }

            // Verify password using BCrypt
            boolean passwordMatches = userService.matchPassword(password, user.getPassword());

            if (passwordMatches) {
                model.addAttribute("user", user);
                return "welcome.jsp";
            } else {
                model.addAttribute("error", "Invalid email or password");
                return "error.jsp";
            }

        } catch (RuntimeException e) {
            model.addAttribute("error", "Invalid user");
            return "error.jsp";
        }
    }
}

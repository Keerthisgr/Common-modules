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
        System.out.println("controller default constructor is invoked");
    }

    @Autowired
    UserService userService;

    @RequestMapping("addUser")
    public String addUser(UserDto dto, Model model) throws InvocationTargetException, IllegalAccessException {

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
            UserDto user = userService.getPasswordByEmail(email, password);
            model.addAttribute("user", user);
            return "welcome.jsp";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Invalid user");
            return "error.jsp";
        }
    }
}

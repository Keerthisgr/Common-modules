package com.xworkz.userapp.restcontroller;

import com.xworkz.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class RestController {
    public RestController() {
        log.info("RestController object created");
    }
    @Autowired
    UserService userService;

    @GetMapping(value = "/checkValue/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNameCount(@PathVariable String name){
        System.out.println("This is restController name: "+name);
        boolean exist = userService.existsByName(name);
        if(exist){
            return "Name exists";
        }
        return "";
    }
}

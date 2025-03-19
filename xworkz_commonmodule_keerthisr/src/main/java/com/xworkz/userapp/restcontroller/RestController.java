package com.xworkz.userapp.restcontroller;

import com.xworkz.userapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
//    @GetMapping(value = "/checkEmailValue/{email}")
//    public String getExistEmail(@PathVariable String email){
//        System.out.println("This is restController email: "+email);
//        boolean exist = userService.existsByEmail(email);
//        if(exist){
//            return "Email exists";
//        }
//        return "";
//    }
//    @GetMapping(value = "/checkPhoneNumberValue/{phoneNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String getExistPhoneNumber(@PathVariable String phoneNumber){
//        System.out.println("This is restController phoneNumber: "+phoneNumber);
//        boolean exist = userService.existsByPhone(phoneNumber);
//        if(exist){
//            return "PhoneNumber exists";
//        }
//        return "";
//    }
@GetMapping(value = "/email/{email}")
@ResponseBody
public String checkEmailAvailability(@PathVariable("email") String email) {
    log.info("Checking availability for email: {}", email);

    long count = userService.getCountByEmail(email);

    if (count > 0) {
        return "Email already registered.";
    }

    return "";
}
    @GetMapping(value = "/phone/{phoneNumber}")
    @ResponseBody
    public String checkPhoneNumberAvailability(@PathVariable("phoneNumber") String phoneNumber) {
        log.info("Checking availability for phone number: {}", phoneNumber);

        long count = userService.getCountByPhoneNumber(phoneNumber);

        if (count > 0) {
            return "Phone number already registered.";
        }

        return "";
}
}

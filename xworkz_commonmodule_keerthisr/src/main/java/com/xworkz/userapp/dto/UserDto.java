package com.xworkz.userapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String location;
    private String gender;
    private String dOB;
    private Integer age;

}
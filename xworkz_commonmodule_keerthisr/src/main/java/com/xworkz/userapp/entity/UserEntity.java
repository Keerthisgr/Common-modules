package com.xworkz.userapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user_info")
@NamedQuery(name = "getPasswordByEmail", query = "SELECT entity FROM UserEntity entity WHERE entity.email = :email")
@NamedQuery(name = "UserEntity.updateByEmail", query = "UPDATE UserEntity u SET u.name = :name, u.phoneNumber = :phoneNumber, u.location = :location, u.age = :age, u.password = :password WHERE u.email = :email")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    @Column(name = "confirm_password")
    private String confirmPassword;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String location;
    private String gender;
    private String dOB;
    private Integer age;
}
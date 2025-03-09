package com.xworkz.userapp.repository;

import com.xworkz.userapp.entity.UserEntity;

public interface UserRepository {
    boolean saveUser(UserEntity entity);
    UserEntity fetchPasswordByEmail(String email);
    UserEntity findByEmail(String email);
    int updateByEmail(String email, String name, String phoneNumber, String location, int age, String password);
}
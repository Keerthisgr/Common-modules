package com.xworkz.userapp.repository;

import com.xworkz.userapp.entity.UserEntity;

public interface UserRepository {
    boolean saveUser(UserEntity entity);
    UserEntity fetchPasswordByEmail(String email);
}

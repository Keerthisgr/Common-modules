package com.xworkz.userapp.repository;

import com.xworkz.userapp.entity.UserEntity;

import java.time.LocalDateTime;

public interface UserRepository {
    boolean saveUser(UserEntity entity);
    UserEntity fetchPasswordByEmail(String email);
    UserEntity findByEmail(String email);
    int updateByEmail(String email, String name, String phoneNumber, String location, int age, String password);
    void updateFailedAttempts(String email, int attempts);
    void lockAccount(String email, LocalDateTime lockTime);
    void resetAttempts(String email);
    void updatePassword(String email, String newPassword);
    boolean existsByNameEmailOrPhone(String name, String email, String phoneNumber);
    void updateProfile(UserEntity userEntity);
    boolean existsByPhone(String phoneNumber);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    boolean deleteByEmail(String email);

    long getCountOfEmail(String email);

    long getCountOfPhoneNumber(String phoneNumber);
}
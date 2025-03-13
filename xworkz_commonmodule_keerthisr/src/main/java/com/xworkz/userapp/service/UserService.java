package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import org.springframework.ui.Model;

public interface UserService {

    String generateRandomPassword();
    String encryption(String password);
    String matchPassword(String encryptedPassword);
    boolean validateAndUser(UserDto dto, Model model) throws Exception;
    UserDto authenticateUser(String email, String password);
    boolean resetPassword(String email, String newPassword);
    boolean saveUserWithPassword(UserDto dto, String password, Model model);
    String validateAndLogIn(String email, String password);
    UserDto getUserByEmail(String email);
    boolean updateUserByEmail(String email, UserDto dto, Model model);
    boolean deleteUserByEmail(String email, Model model);
    boolean isPasswordCorrect(String email, String password);
    void resetFailedAttempts(String email);

}

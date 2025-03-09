package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.entity.UserEntity;
import com.xworkz.userapp.repository.UserRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    UserRepository repository;

    @Override
    public boolean validateAndUser(UserDto dto, Model model) throws InvocationTargetException, IllegalAccessException {
        boolean errors = false;
        if (dto == null) {
            model.addAttribute("error", "User details cannot be null.");
            return false;
        }



        String nameRegex = "^[A-Z][a-zA-Z ]{2,49}$";
        Pattern namePattern = Pattern.compile(nameRegex);

        if (dto.getName() == null || !namePattern.matcher(dto.getName()).matches()) {
            model.addAttribute("nameError", "Invalid Name: Must start with an uppercase letter and contain only letters and spaces.");
            errors = true;
        }



        String phoneRegex = "^[9876]\\d{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);

        if (dto.getPhoneNumber() == null || !phonePattern.matcher(String.valueOf(dto.getPhoneNumber())).matches()) {
            model.addAttribute("phoneError", "Invalid Phone Number");
            errors= true;
        }


        if (dto.getPassword() == null || dto.getConfirmPassword() == null ||
                !dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Password and Confirm Password must be the same.");
            errors= true;
        }

        String emailRegex = "^(?=.*[!@#$%^&*])[a-z0-9]+@gmail\\.com$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        if (dto.getEmail() == null || !emailPattern.matcher(dto.getEmail()).matches()) {
            model.addAttribute("emailError", "Invalid Email");
            errors= true;
        }


        if(errors){
            return false;
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(entity, dto);
        repository.saveUser(entity);
        return true;
    }

    @Override
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    @Override
    public boolean matchPassword(String enteredPassword, String storedHash) {
        return BCrypt.checkpw(enteredPassword, storedHash);
    }

    @Override
    public UserDto getPasswordByEmail(String email, String enteredPassword) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = repository.fetchPasswordByEmail(email);

        if (userEntity == null) {
            return null;
        }

        try {
            BeanUtils.copyProperties(userDto, userEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }


        if (!matchPassword(enteredPassword, userEntity.getPassword())) {
            return null;
        }

        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email);
        if (userEntity == null) return null;

        UserDto dto = new UserDto();
        try {
            BeanUtils.copyProperties(dto, userEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Error copying properties: " + e.getMessage());
        }
        return dto;
    }
@Transactional
    @Override
    public boolean updateUserByEmail(String email, UserDto dto, Model model) {
    System.out.println("In service updateUserByEmail started");
        UserEntity existingUser = repository.findByEmail(email);

        if (existingUser == null) {
            model.addAttribute("error", "User not found.");
            return false;
        }


        String newPassword = existingUser.getPassword();
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            newPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(12));
        }


        int rowsUpdated = repository.updateByEmail(
                email,
                dto.getName(),
                String.valueOf(dto.getPhoneNumber()),
                dto.getLocation(),
                dto.getAge(),
                newPassword
        );
        System.out.println("service is getting updated repo rows" + rowsUpdated);
        if (rowsUpdated > 0) {
            model.addAttribute("successMessage", "Profile updated successfully!");
            System.out.println("In service updateUserByEmail started");
            return true;
        } else {
            model.addAttribute("error", "Failed to update profile.");
            return false;
        }
    }


}
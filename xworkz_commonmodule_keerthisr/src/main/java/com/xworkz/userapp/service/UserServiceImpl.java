package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.entity.UserEntity;
import com.xworkz.userapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;


    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$!%*?&";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return password.toString();
    }


    @Override
    public String encryption(String password) {
        StringBuilder encrypt = new StringBuilder();
        for (char ch : password.toCharArray()) {
            encrypt.append((char) (ch + 3));
        }
        return encrypt.toString();
    }


    @Override
    public String matchPassword(String encryptedPassword) {
        StringBuilder decrypt = new StringBuilder();
        for (char ch : encryptedPassword.toCharArray()) {
            decrypt.append((char) (ch - 3));
        }
        return decrypt.toString();
    }

    @Override
    public boolean validateAndUser(UserDto dto, Model model) throws InvocationTargetException, IllegalAccessException {
        boolean hasErrors = false;

        if (dto == null) {
            model.addAttribute("error", "User details cannot be null.");
            return false;
        }

        // Name validation
        String nameRegex = "^[A-Z][a-zA-Z ]{2,49}$";
        Pattern namePattern = Pattern.compile(nameRegex);
        if (dto.getName() == null || !namePattern.matcher(dto.getName()).matches()) {
            model.addAttribute("nameError", "Invalid Name");
            hasErrors = true;
        }

        // Phone number validation
        String phoneRegex = "^[9876]\\d{9}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        if (dto.getPhoneNumber() == null || !phonePattern.matcher(String.valueOf(dto.getPhoneNumber())).matches()) {
            model.addAttribute("phoneError", "Invalid Phone Number");
            hasErrors = true;
        }



        // Email validation
        String emailRegex = "^[a-z0-9]+@gmail\\.com$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (dto.getEmail() == null || !emailPattern.matcher(dto.getEmail()).matches()) {
            model.addAttribute("emailError", "Invalid Email:(example@gmail.com).");
            hasErrors = true;
        }

        // Age validation
        if (dto.getAge() > 95) {
            model.addAttribute("ageError", "Invalid Age: Age must be 95 or below.");
            hasErrors = true;
        }

        if (hasErrors) {
            return false;
        }

        // Generate and encrypt password
        String generatedPassword = generateRandomPassword();
        dto.setPassword(encryption(generatedPassword));

        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(entity, dto);
        entity.setPassword(encryption(generatedPassword));
        entity.setFailedAttempts(0);

        boolean saved = saveEmail(dto.getEmail(),generatedPassword);
        System.out.println("Generated Password in service: " + generatedPassword);
        if(saved){
            System.out.println("Email sent");
        }
        repository.saveUser(entity);
        return true;
    }


    @Override
    public UserDto authenticateUser(String email, String password) {
        UserEntity userEntity = repository.findByEmail(email);

        if (userEntity == null) {
            log.info("User not found");
            return null;
        }


        if (userEntity.isAccountLocked()) {
            LocalDateTime lockedAt = userEntity.getLockTime();
            if (lockedAt != null && lockedAt.plusHours(24).isAfter(LocalDateTime.now())) {
                log.info("Account is still locked.");
                return null;
            } else {
                log.info("Unlocking account after 24 hours.");
                repository.resetAttempts(email);
            }
        }


        String decryptedPassword = matchPassword(userEntity.getPassword());

        if (!password.equals(decryptedPassword)) {
            int attempts = userEntity.getFailedAttempts() + 1;
            log.info("Failed login attempt count: " + attempts);

            if (attempts >= 3) {
                log.info("Locking account...");
                repository.lockAccount(email, LocalDateTime.now());
            } else {
                repository.updateFailedAttempts(email, attempts);
            }
            return null;
        }

        log.info("Resetting failed attempts on successful login.");
        repository.resetAttempts(email);

        UserDto userDto = new UserDto();
        try {
            BeanUtils.copyProperties(userDto, userEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return userDto;
    }

    @Override
    @Transactional
    public boolean resetPassword(String email, String newPassword) {
        UserEntity userEntity = repository.findByEmail(email);
        if (userEntity == null) {
            log.warn("User not found: " + email);
            return false;
        }

        // Encrypt the new password
        String encryptedPassword = encryption(newPassword);
        repository.updatePassword(email, encryptedPassword);
        repository.resetAttempts(email);

        log.info("Password reset successfully for " + email);
        return true;
    }

    @Override
    public boolean saveUserWithPassword(UserDto dto, String password, Model model) {
        boolean exists = false;

        if (repository.existsByEmail(dto.getEmail())) {
            model.addAttribute("emailError", "Email already exists.");
            exists = true;
        }
        if (repository.existsByName(dto.getName())) {
            model.addAttribute("nameError", "Name already exists.");
            exists = true;
        }
        if (repository.existsByPhone(dto.getPhoneNumber())) {
            model.addAttribute("phoneError", "Phone number already exists.");
            exists = true;
        }

        if (exists) {
            return false; // Stop execution if any of the fields already exist.
        }

        // Encrypt password before saving
        String encryptedPassword = encryption(password);
        UserEntity entity = new UserEntity();
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setPassword(encryptedPassword);
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setLocation(dto.getLocation());
        entity.setAge(dto.getAge());
        entity.setDOB(dto.getDOB());
        entity.setGender(dto.getGender());
        entity.setFailedAttempts(-1);

        repository.saveUser(entity);
        return true;
    }


    @Override
    public String validateAndLogIn(String email, String password) {
        log.info("Validating login for: " + email);


        UserEntity entity = repository.findByEmail(email);
        if (entity == null) {
            return "Invalid email";
        }

        if (entity.isAccountLocked()) {
            if (Duration.between(entity.getLockTime(), Instant.now()).toHours() >= 24) {
                entity.setAccountLocked(false);
                entity.setFailedAttempts(0);
                repository.updateProfile(entity);
            } else {
                return "Account is locked. Try again after 24 hours.";
            }
        }

        String decryptedPassword = matchPassword(entity.getPassword());

        if (password.equals(decryptedPassword)) {
            entity.setFailedAttempts(0);
            repository.updateProfile(entity);
            return "isPresent";
        } else {
            int attempts = entity.getFailedAttempts() + 1;
            entity.setFailedAttempts(attempts);

            if (attempts >= 3) {
                entity.setAccountLocked(true);
            }

            repository.updateProfile(entity);
            return (attempts == 3) ? "Account is locked for 24 hours." : "Invalid password. Attempts left: " + (3 - attempts);
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email);

        if (userEntity == null) {
            return null;
        }

        UserDto dto = new UserDto();
        try {
            BeanUtils.copyProperties(dto, userEntity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error copying properties: " + e.getMessage());
        }
        return dto;
    }


    @Override
    @Transactional
    public boolean updateUserByEmail(String email, UserDto dto, Model model) {
        UserEntity existingUser = repository.findByEmail(email);
        if (existingUser == null) {
            model.addAttribute("error", "User not found.");
            return false;
        }

        String newPassword = existingUser.getPassword();
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            newPassword = encryption(dto.getPassword());
        }

        int rowsUpdated = repository.updateByEmail(
                email,
                dto.getName(),
                String.valueOf(dto.getPhoneNumber()),
                String.valueOf(dto.getLocation()),
                dto.getAge(),
                newPassword
        );

        if (rowsUpdated > 0) {
            model.addAttribute("successMessage", "Profile updated successfully!");
            return true;
        } else {
            model.addAttribute("error", "Failed to update profile.");
            return false;
        }
    }
        public boolean saveEmail(String email, String generatedPassword) {
            final String username = "keerthisr.xworkz@gmail.com";
            final String password = "kplz dnjm fkit xufm";

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(prop, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Email Verification");
                message.setText("Dear User, your password is: " + generatedPassword);
                Transport.send(message);
                System.out.println("Email sent: "+email);
            } catch (MessagingException e) {
                e.printStackTrace();

            }return false;
        }

    @Override
    public boolean isPasswordCorrect(String email, String enteredPassword) {
        UserEntity userEntity = repository.findByEmail(email);
        if (userEntity == null) {
            return false;
        }
        String decryptedPassword = matchPassword(userEntity.getPassword());
        return decryptedPassword.equals(enteredPassword);
    }
    @Override
    @Transactional
    public void resetFailedAttempts(String email) {
        repository.resetAttempts(email);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public long getCountByEmail(String email) {
        long count = repository.getCountOfEmail(email);
        log.info("Count: "+count);
        return count;
    }

    @Override
    public long getCountByPhoneNumber(String phoneNumber) {
        long count = repository.getCountOfPhoneNumber(phoneNumber);
        log.info("Count: "+count);
        return count;
    }


    @Override
    public boolean deleteUserByEmail(String email, Model model) {
        UserEntity userEntity = repository.findByEmail(email);
        if (userEntity == null) {
            model.addAttribute("error", "User not found with email: " + email);
            return false;
        }

        boolean isDeleted = repository.deleteByEmail(email);
        if (isDeleted) {
            model.addAttribute("successMessage", "User deleted successfully.");
            return true;
        } else {
            model.addAttribute("error", "Failed to delete user.");
            return false;
        }
    }
    }


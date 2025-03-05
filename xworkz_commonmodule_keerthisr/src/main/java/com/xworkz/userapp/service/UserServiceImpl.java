package com.xworkz.userapp.service;

import com.xworkz.userapp.dto.UserDto;
import com.xworkz.userapp.entity.UserEntity;
import com.xworkz.userapp.repository.UserRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Override
    public boolean validateAndUser(UserDto dto) throws InvocationTargetException, IllegalAccessException {
        UserEntity entity = new UserEntity();
        if(dto != null)
            BeanUtils.copyProperties(entity,dto);
        repository.saveUser(entity);
        return true;
    }

}

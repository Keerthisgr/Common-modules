package com.xworkz.userapp.repository;

import com.xworkz.userapp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Override
    public boolean saveUser(UserEntity entity) {
        EntityManager entityManager =  entityManagerFactory.createEntityManager() ;
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }
}

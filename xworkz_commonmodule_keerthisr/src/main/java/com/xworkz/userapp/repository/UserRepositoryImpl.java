package com.xworkz.userapp.repository;

import com.xworkz.userapp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;

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

    @Override
    public UserEntity fetchPasswordByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserEntity userEntity = (UserEntity) entityManager.createNamedQuery("getPasswordByEmail").setParameter("email", email).getSingleResult();
        return userEntity;
    }

    @Override
    public UserEntity findByEmail(String email) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            return entityManager.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
//    @Transactional
    public int updateByEmail(String email, String name, String phoneNumber, String location, int age, String password) {
        System.out.println("Repo updateByEmail started");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        int noOfRowsUpdated = 0;
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery(
                    "UPDATE UserEntity u SET u.name = :name, u.phoneNumber = :phoneNumber, " +
                            "u.location = :location, u.age = :age, u.password = :password WHERE u.email = :email");

            query.setParameter("name", name);
            query.setParameter("phoneNumber", phoneNumber);
            query.setParameter("location", location);
            query.setParameter("age", age);
            query.setParameter("password", password);
            query.setParameter("email", email);
            noOfRowsUpdated = query.executeUpdate();
            System.out.println("noOfRowsUpdated: " + noOfRowsUpdated);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("error in repo " + e.getMessage());
        } finally {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
        }
        System.out.println("Repo updateByEmail ended");
        return noOfRowsUpdated;

    }
}
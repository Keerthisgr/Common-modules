package com.xworkz.userapp.repository;

import com.xworkz.userapp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Return null if user not found
        } finally {
            entityManager.close();
        }
    }


    @Override
    @Transactional
    public int updateByEmail(String email, String name, String phoneNumber, String location, int age, String password) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        int rowsUpdated = 0;
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

            rowsUpdated = query.executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error updating user: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return rowsUpdated;
    }



    @Override
    public void updateFailedAttempts(String email, int attempts) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE UserEntity u SET u.failedAttempts = :attempts WHERE u.email = :email")
                .setParameter("attempts", attempts)
                .setParameter("email", email)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void lockAccount(String email, LocalDateTime lockTime) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE UserEntity u SET u.accountLocked = true, u.lockTime = :lockTime WHERE u.email = :email")
                .setParameter("lockTime", lockTime)
                .setParameter("email", email)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void resetAttempts(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE UserEntity u SET u.failedAttempts = 0, u.accountLocked = false, u.lockTime = null WHERE u.email = :email")
                .setParameter("email", email)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE UserEntity u SET u.password = :password WHERE u.email = :email")
                .setParameter("password", newPassword)
                .setParameter("email", email)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    @Override
    public boolean existsByNameEmailOrPhone(String name, String email, String phoneNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        boolean exists = false;
        try {
            Query query = entityManager.createQuery(
                    "SELECT COUNT(u) FROM UserEntity u WHERE u.name = :name OR u.email = :email OR u.phoneNumber = :phoneNumber"
            );
            query.setParameter("name", name);
            query.setParameter("email", email);
            query.setParameter("phoneNumber", phoneNumber);

            Long count = (Long) query.getSingleResult();
            exists = count > 0;
        } catch (Exception e) {
            System.out.println("Error checking existing user: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return exists;
    }

    @Override
    public boolean existsByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email");
            query.setParameter("email", email);
            return (Long) query.getSingleResult() > 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean existsByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(u) FROM UserEntity u WHERE u.name = :name");
            query.setParameter("name", name);
            return (Long) query.getSingleResult() > 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean existsByPhone(String phoneNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT COUNT(u) FROM UserEntity u WHERE u.phoneNumber = :phoneNumber");
            query.setParameter("phoneNumber", phoneNumber);
            return (Long) query.getSingleResult() > 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    @Transactional
    public void updateProfile(UserEntity userEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery(
                            "UPDATE UserEntity u SET u.failedAttempts = :failedAttempts, u.accountLocked = :accountLocked, u.lockTime = :lockTime WHERE u.email = :email"
                    )
                    .setParameter("failedAttempts", userEntity.getFailedAttempts())
                    .setParameter("accountLocked", userEntity.isAccountLocked())
                    .setParameter("lockTime", userEntity.isAccountLocked() ? userEntity.getLockTime() : null)
                    .setParameter("email", userEntity.getEmail())
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error updating user profile: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }
    @Override
    @Transactional
    public boolean deleteByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE FROM UserEntity u WHERE u.email = :email");
            query.setParameter("email", email);
            int rowsDeleted = query.executeUpdate();
            entityManager.getTransaction().commit();
            return rowsDeleted > 0;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

}
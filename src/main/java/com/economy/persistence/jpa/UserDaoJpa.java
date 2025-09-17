package com.economy.persistence.jpa;

import java.util.Optional;

import com.economy.model.User;
import com.economy.persistence.dao.UserDao;

import jakarta.persistence.EntityManager;

public class UserDaoJpa extends GenericDaoJpa<User, Long> implements UserDao {
    
    public UserDaoJpa() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (EntityManager em = super.em()) {
            return em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultStream()
                    .findFirst();
        }
    }

}

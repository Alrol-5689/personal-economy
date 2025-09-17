package com.economy.persistence.dao;

import java.util.Optional;

import com.economy.model.User;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByUsername(String username);
}

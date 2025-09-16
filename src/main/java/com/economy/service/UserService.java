package com.economy.service;

import com.economy.model.User;
import com.economy.persistence.dao.UserDao;
import com.economy.persistence.jpa.UserDaoJpa;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class UserService {
    
    private final UserDao userDao;
    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

    public UserService() {
        this.userDao = new UserDaoJpa(); 
    }

    public void createUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");  
        var violations = VALIDATOR.validate(user);
        if (!violations.isEmpty()) throw new IllegalArgumentException("User data is not valid: " + violations);        
        userDao.create(user);
    }
}



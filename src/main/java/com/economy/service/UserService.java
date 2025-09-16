package com.economy.service;

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
}



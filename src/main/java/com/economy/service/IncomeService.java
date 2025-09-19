package com.economy.service;

import com.economy.model.Income;
import com.economy.model.User;
import com.economy.persistence.dao.IncomeDao;
import com.economy.persistence.jpa.IncomeDaoJpa;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class IncomeService {

    private final IncomeDao incomeDao;
    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

    public IncomeService() {
        this.incomeDao = new IncomeDaoJpa(); 
    }

    public void createIncome(Income income, Long userId) {
        if (income == null) throw new IllegalArgumentException("Income cannot be null");
        if (userId == null) throw new IllegalArgumentException("User id is required");

        if (income.getUser() == null) {
            User owner = new User();
            owner.setId(userId);
            income.setUser(owner);
        }

        var violations = VALIDATOR.validate(income);
        if (!violations.isEmpty()) throw new IllegalArgumentException("Income data is not valid: " + violations);        
        incomeDao.create(income, userId);
    }
    
}

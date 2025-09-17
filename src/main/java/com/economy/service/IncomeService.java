package com.economy.service;

import com.economy.model.Income;
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
        var violations = VALIDATOR.validate(income);
        if (!violations.isEmpty()) throw new IllegalArgumentException("Income data is not valid: " + violations);        
        incomeDao.create(income, userId);
    }
    
}

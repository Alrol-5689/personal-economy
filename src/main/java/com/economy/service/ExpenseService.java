package com.economy.service;

import com.economy.model.Expense;
import com.economy.persistence.dao.ExpenseDao;
import com.economy.persistence.jpa.ExpenseDaoJpa;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ExpenseService {

    private final ExpenseDao expenseDao;
    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();
    
    public ExpenseService() {
        this.expenseDao = new ExpenseDaoJpa(); 
    }

    public void create(Expense expense, Long userId) {
        if (expense == null) throw new IllegalArgumentException("Expense cannot be null");  
        var violations = VALIDATOR.validate(expense);
        if (!violations.isEmpty()) throw new IllegalArgumentException("Expense data is not valid: " + violations);        
        expenseDao.create(expense, userId);
    }
    
}

package com.economy.service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.economy.model.MonthlyCapital;
import com.economy.model.User;
import com.economy.persistence.dao.MonthlyCapitalDao;
import com.economy.persistence.jpa.MonthlyCapitalDaoJpa;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class MonthlyCapitalService {

    private final MonthlyCapitalDao monthlyCapitalDao;
    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

    public MonthlyCapitalService() {
        this.monthlyCapitalDao = new MonthlyCapitalDaoJpa();
    }

    public MonthlyCapital create(MonthlyCapital capital, Long userId) {
        if (capital == null) throw new IllegalArgumentException("Capital snapshot cannot be null");
        if (userId == null) throw new IllegalArgumentException("User id is required");

        if (capital.getUser() == null) {
            User owner = new User();
            owner.setId(userId);
            capital.setUser(owner);
        }

        var violations = VALIDATOR.validate(capital);
        if (!violations.isEmpty()) throw new IllegalArgumentException("Capital data is not valid: " + violations);
        return monthlyCapitalDao.saveForUser(capital, userId);
    }

    public Optional<MonthlyCapital> findByUserAndMonth(Long userId, YearMonth month) {
        return monthlyCapitalDao.findByUserAndMonth(userId, month);
    }

    public List<MonthlyCapital> findByUser(Long userId) {
        return monthlyCapitalDao.findByUser(userId);
    }
}

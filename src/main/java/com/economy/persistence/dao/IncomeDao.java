package com.economy.persistence.dao;

import java.util.List;

import com.economy.model.Income;

public interface IncomeDao extends GenericDao<Income, Long> {
    // Puedes agregar métodos específicos para Income si es necesario

    void create(Income income, Long userId);

    List<Income> findByMonthAndYear(int month, int year, Long userId);
}
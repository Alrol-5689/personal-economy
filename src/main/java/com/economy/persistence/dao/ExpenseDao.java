package com.economy.persistence.dao;

import java.util.List;

import com.economy.model.Expense;

public interface ExpenseDao extends GenericDao<Expense, Long> {

    void create(Expense expense, Long userId);

    List<Expense> findByUser(Long userId);
}

package com.economy.persistence.dao;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.economy.model.MonthlyCapital;

public interface MonthlyCapitalDao extends GenericDao<MonthlyCapital, Long> {

    Optional<MonthlyCapital> findByUserAndMonth(Long userId, YearMonth month);

    List<MonthlyCapital> findByUser(Long userId);

    MonthlyCapital saveForUser(MonthlyCapital capital, Long userId);
}

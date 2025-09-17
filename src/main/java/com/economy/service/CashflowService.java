package com.economy.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.economy.model.Expense;
import com.economy.model.Income;
import com.economy.model.MonthlyCapital;
import com.economy.persistence.dao.ExpenseDao;
import com.economy.persistence.dao.IncomeDao;
import com.economy.persistence.dao.MonthlyCapitalDao;
import com.economy.persistence.jpa.ExpenseDaoJpa;
import com.economy.persistence.jpa.IncomeDaoJpa;
import com.economy.persistence.jpa.MonthlyCapitalDaoJpa;

public class CashflowService {

    private final IncomeDao incomeDao = new IncomeDaoJpa();
    private final ExpenseDao expenseDao = new ExpenseDaoJpa();
    private final MonthlyCapitalDao capitalDao = new MonthlyCapitalDaoJpa();

    public CashflowReport buildReport(Long userId) {
        List<Income> incomes = incomeDao.findByUser(userId);
        List<Expense> expenses = expenseDao.findByUser(userId);
        List<MonthlyCapital> capital = capitalDao.findByUser(userId);

        Map<YearMonth, MonthSummary> byMonth = new TreeMap<>();

        for (Income income : incomes) {
            MonthSummary summary = byMonth.computeIfAbsent(extractMonth(income.getDate()), MonthSummary::new);
            summary.addIncome(income);
        }

        for (Expense expense : expenses) {
            MonthSummary summary = byMonth.computeIfAbsent(extractMonth(expense.getDate()), MonthSummary::new);
            summary.addExpense(expense);
        }

        for (MonthlyCapital snapshot : capital) {
            MonthSummary summary = byMonth.computeIfAbsent(snapshot.getMonth(), MonthSummary::new);
            summary.setCapitalSnapshot(snapshot);
        }

        List<MonthSummary> ordered = new ArrayList<>(byMonth.values());
        double cumulative = 0.0;
        for (int i = 0; i < ordered.size(); i++) {
            MonthSummary summary = ordered.get(i);
            cumulative += summary.getNetBalance();
            summary.setCumulativeNet(cumulative);

            double expenseSum = 0.0;
            int countedMonths = 0;
            for (int j = Math.max(0, i - 11); j <= i; j++) {
                MonthSummary past = ordered.get(j);
                expenseSum += past.getTotalExpenses();
                countedMonths++;
            }
            summary.setTrailingExpenseAverage(countedMonths > 0 ? expenseSum / countedMonths : 0.0);
        }

        return new CashflowReport(ordered);
    }

    private static YearMonth extractMonth(LocalDate date) {
        return YearMonth.from(date);
    }

    public static final class CashflowReport {
        private final List<MonthSummary> months;
        private Map<Integer, List<MonthSummary>> cacheByYear;

        CashflowReport(List<MonthSummary> months) {
            this.months = months;
        }

        public List<MonthSummary> getMonths() {
            return months;
        }

        public Map<Integer, List<MonthSummary>> getMonthsByYear() {
            if (cacheByYear == null) {
                cacheByYear = new LinkedHashMap<>();
                for (MonthSummary summary : months) {
                    int year = summary.getMonth().getYear();
                    cacheByYear.computeIfAbsent(year, y -> new ArrayList<>()).add(summary);
                }
            }
            return cacheByYear;
        }
    }

    public static final class MonthSummary {
        private final YearMonth month;
        private double activeIncome;
        private double capitalGainIncome;
        private double passiveIncome;
        private double otherIncome;
        private double fixedExpenses;
        private double necessaryExpenses;
        private double discretionaryExpenses;
        private double cumulativeNet;
        private double trailingExpenseAverage;
        private MonthlyCapital capitalSnapshot;

        MonthSummary(YearMonth month) {
            this.month = month;
        }

        public YearMonth getMonth() { return month; }

        public int getYear() { return month.getYear(); }

        public String getMonthLabel() {
            return month.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        }

        public double getActiveIncome() { return activeIncome; }
        public double getCapitalGainIncome() { return capitalGainIncome; }
        public double getPassiveIncome() { return passiveIncome; }
        public double getOtherIncome() { return otherIncome; }

        public double getFixedExpenses() { return fixedExpenses; }
        public double getNecessaryExpenses() { return necessaryExpenses; }
        public double getDiscretionaryExpenses() { return discretionaryExpenses; }

        public double getTotalIncome() {
            return activeIncome + capitalGainIncome + passiveIncome + otherIncome;
        }

        public double getTotalExpenses() {
            return fixedExpenses + necessaryExpenses + discretionaryExpenses;
        }

        public double getNetBalance() {
            return getTotalIncome() - getTotalExpenses();
        }

        public double getDiscretionaryPercentage() {
            double totalExpense = getTotalExpenses();
            if (totalExpense <= 0.0) return 0.0;
            return (discretionaryExpenses / totalExpense) * 100.0;
        }

        public double getCumulativeNet() { return cumulativeNet; }
        void setCumulativeNet(double cumulativeNet) { this.cumulativeNet = cumulativeNet; }

        public double getTrailingExpenseAverage() { return trailingExpenseAverage; }
        void setTrailingExpenseAverage(double trailingExpenseAverage) { this.trailingExpenseAverage = trailingExpenseAverage; }

        public MonthlyCapital getCapitalSnapshot() { return capitalSnapshot; }
        void setCapitalSnapshot(MonthlyCapital capitalSnapshot) { this.capitalSnapshot = capitalSnapshot; }

        public double getRunwayMonths() {
            if (capitalSnapshot == null) return 0.0;
            double burnRate = trailingExpenseAverage;
            if (burnRate <= 0.0) return 0.0;
            return capitalSnapshot.getTotalAssets() / burnRate;
        }

        void addIncome(Income income) {
            if (income == null) return;
            Income.IncomeType type = income.getType();
            double amount = safeAmount(income.getAmount());
            if (type == null) {
                otherIncome += amount;
                return;
            }
            switch (type) {
                case ACTIVE -> activeIncome += amount;
                case CAPITAL_GAIN -> capitalGainIncome += amount;
                case PASSIVE -> passiveIncome += amount;
                case OTHER -> otherIncome += amount;
                default -> otherIncome += amount;
            }
        }

        void addExpense(Expense expense) {
            if (expense == null) return;
            double amount = safeAmount(expense.getNetAmount());
            if (amount <= 0.0) return;
            Expense.ExpenseType type = expense.getType();
            if (type == null) {
                discretionaryExpenses += amount;
                return;
            }
            switch (type) {
                case FIXED -> fixedExpenses += amount;
                case ESSENTIAL -> necessaryExpenses += amount;
                case DISCRETIONARY -> discretionaryExpenses += amount;
                default -> discretionaryExpenses += amount;
            }
        }

        private double safeAmount(Double amount) {
            return amount == null ? 0.0 : amount;
        }
    }
}

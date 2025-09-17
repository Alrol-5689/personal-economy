package com.economy.model;

import java.time.YearMonth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "monthly_capital", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "month"})
})
public class MonthlyCapital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "month", nullable = false)
    private YearMonth month;

    @Column(name = "savings_account", nullable = false)
    private double savingsAccount;

    @Column(name = "piggy_bank", nullable = false)
    private double piggyBank;

    @Column(name = "checking_account", nullable = false)
    private double checkingAccount;

    @Column(name = "cash", nullable = false)
    private double cash;

    public MonthlyCapital() {}

    public MonthlyCapital(User user, YearMonth month) {
        this.user = user;
        this.month = month;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public long getVersion() { return version; }
    public void setVersion(long version) { this.version = version; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public YearMonth getMonth() { return month; }
    public void setMonth(YearMonth month) { this.month = month; }

    public double getSavingsAccount() { return savingsAccount; }
    public void setSavingsAccount(double savingsAccount) { this.savingsAccount = savingsAccount; }

    public double getPiggyBank() { return piggyBank; }
    public void setPiggyBank(double piggyBank) { this.piggyBank = piggyBank; }

    public double getCheckingAccount() { return checkingAccount; }
    public void setCheckingAccount(double checkingAccount) { this.checkingAccount = checkingAccount; }

    public double getCash() { return cash; }
    public void setCash(double cash) { this.cash = cash; }

    public double getTotalAssets() {
        return savingsAccount + piggyBank + checkingAccount + cash;
    }
}

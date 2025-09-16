package com.economy.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    
    public static enum ExpenseType {FIXED, ESSENTIAL, DISCRETIONARY}

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;
    
    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ExpenseType type;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();
    
    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reimbursement> reimbursements = new ArrayList<>();
    
    public Expense() {}
    
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;} 
    
    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}
    
    public ExpenseType getType() {return type;}
    public void setType(ExpenseType type) {this.type = type;}

    public List<Reimbursement> getReimbursements() { return reimbursements; }
    public void setReimbursements(List<Reimbursement> reimbursements) { this.reimbursements = reimbursements; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate createdAt) { this.date = createdAt; }

    @Transient
    public double getReimbursedAmount() {
        if (reimbursements == null || reimbursements.isEmpty()) return 0.0;
        return reimbursements.stream()
                .map(Reimbursement::getAmount)
                .filter(v -> v != null)
                .reduce(0.0, Double::sum);
    }

    @Transient
    public double getNetAmount() {
        double base = Objects.requireNonNullElse(amount, 0.0);
        return base - getReimbursedAmount();
    }

}

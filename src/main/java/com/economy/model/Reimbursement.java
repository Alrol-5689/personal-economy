package com.economy.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "reimbursements")
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "payer_name")
    private String payerName; // Quién te hizo el Bizum

    @Column(name = "note")
    private String note; // Comentario opcional

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();

    public Reimbursement() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Expense getExpense() { return expense; }
    public void setExpense(Expense expense) { this.expense = expense; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPayerName() { return payerName; }
    public void setPayerName(String payerName) { this.payerName = payerName; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate createdAt) { this.date = createdAt; }
}

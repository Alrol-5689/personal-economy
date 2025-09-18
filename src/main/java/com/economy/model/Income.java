package com.economy.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    
    public enum IncomeType {
        ACTIVE,
        CAPITAL_GAIN,
        PASSIVE,
        OTHER
    }
    
    public enum IncomeSubType {
        //===>> ACTIVOS <===//
        SALARY(IncomeType.ACTIVE),
        BONUS(IncomeType.ACTIVE),
        FREELANCE(IncomeType.ACTIVE),
        OTHER_ACTIVE(IncomeType.ACTIVE),
        //===>> GANANCIAS DE CAPITAL <===//
        REAL_ESTATE_SALE(IncomeType.CAPITAL_GAIN), // Venta de propiedad
        STOCK_SALE (IncomeType.CAPITAL_GAIN), // Venta de acciones
        BOND_SALE(IncomeType.CAPITAL_GAIN), // Venta de bonos
        MUTUAL_FUND_SALE(IncomeType.CAPITAL_GAIN), // Venta de fondos
        CRYPTO_SALE(IncomeType.CAPITAL_GAIN), // Venta de criptomonedas
        BUSINESS_SALE(IncomeType.CAPITAL_GAIN), // Venta de negocio
        OTHER_CAPITAL_GAIN(IncomeType.CAPITAL_GAIN),
        //===>> PASIVOS <===//
        INTEREST(IncomeType.PASSIVE),
        DIVIDENDS(IncomeType.PASSIVE),
        RENTAL(IncomeType.PASSIVE),
        COMMISSIONS(IncomeType.PASSIVE),
        OTHER_PASSIVE(IncomeType.PASSIVE),
        //===>> OTROS <===//
        OTHER(IncomeType.OTHER);
        
        private final IncomeType parent;
        
        IncomeSubType(IncomeType parent) {this.parent = parent;}
        
        public IncomeType getParent() {return parent;}
    }
    
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @NotNull
    @Positive
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "subtype", nullable = false)
    private IncomeSubType subtype;
    
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey= @ForeignKey(name = "fk_income_user"))
    private User user;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();

    @Transient
    public IncomeType getType() {
        return (subtype != null) ? subtype.getParent() : null;
    }
    
    public boolean isActive() { return getType() == IncomeType.ACTIVE; }
    public boolean isCapitalGain() { return getType() == IncomeType.CAPITAL_GAIN; }
    public boolean isPassive() { return getType() == IncomeType.PASSIVE; }
    
    public Income() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public IncomeSubType getSubtype() { return subtype; }
    public void setSubtype(IncomeSubType subtype) { this.subtype = subtype; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate createdAt) { this.date = createdAt; }
}

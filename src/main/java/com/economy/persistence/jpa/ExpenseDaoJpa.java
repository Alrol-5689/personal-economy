package com.economy.persistence.jpa;

import java.util.List;

import com.economy.model.Expense;
import com.economy.model.User;
import com.economy.persistence.dao.ExpenseDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ExpenseDaoJpa extends GenericDaoJpa<Expense, Long> implements ExpenseDao {
    
    public ExpenseDaoJpa() {
        super(Expense.class);
    }

    @Override
    public void create(Expense expense, Long userId) {
        try (EntityManager em = super.em()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                User userRef = em.getReference(User.class, userId);
                expense.setUser(userRef);
                em.persist(expense);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Expense> findByUser(Long userId) {
        try (EntityManager em = super.em()) {
            return em.createQuery(
                    "select e from Expense e where e.user.id = :userId order by e.date",
                    Expense.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

}

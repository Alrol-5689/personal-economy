package com.economy.persistence.jpa;

import java.time.LocalDate;
import java.util.List;

import com.economy.model.Income;
import com.economy.model.User;
import com.economy.persistence.dao.IncomeDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class IncomeDaoJpa extends GenericDaoJpa<Income, Long> implements IncomeDao {

    public IncomeDaoJpa() {
        super(Income.class);
    }

    @Override
    public void create(Income income, Long userId) {
        // Asigna el usuario por id y persiste
        try (EntityManager em = super.em()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            User userRef = em.getReference(User.class, userId);
            income.setUser(userRef);
            em.persist(income);
            tx.commit();
        }
    }

    @Override
    public List<Income> findByMonthAndYear(int month, int year, Long userId) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate endExclusive = start.plusMonths(1); // [start, end)

        try (EntityManager em = super.em()) {
            return em.createQuery(
                    "select i from Income i where i.user.id = :userId and i.createdAt >= :start and i.createdAt < :end",
                    Income.class)
                    .setParameter("userId", userId)
                    .setParameter("start", start)
                    .setParameter("end", endExclusive)
                    .getResultList();
        }
    }
}

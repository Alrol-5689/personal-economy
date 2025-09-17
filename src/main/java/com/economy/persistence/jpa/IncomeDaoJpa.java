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
        try (EntityManager em = super.em()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                User userRef = em.getReference(User.class, userId);
                income.setUser(userRef);
                em.persist(income);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Income> findByMonthAndYear(int month, int year, Long userId) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate endExclusive = start.plusMonths(1);

        try (EntityManager em = super.em()) {
            return em.createQuery(
                    "select i from Income i where i.user.id = :userId and i.date >= :start and i.date < :end order by i.date",
                    Income.class)
                    .setParameter("userId", userId)
                    .setParameter("start", start)
                    .setParameter("end", endExclusive)
                    .getResultList();
        }
    }

    @Override
    public List<Income> findByUser(Long userId) {
        try (EntityManager em = super.em()) {
            return em.createQuery(
                    "select i from Income i where i.user.id = :userId order by i.date",
                    Income.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
}

package com.economy.persistence.jpa;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.economy.model.MonthlyCapital;
import com.economy.model.User;
import com.economy.persistence.dao.MonthlyCapitalDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MonthlyCapitalDaoJpa extends GenericDaoJpa<MonthlyCapital, Long> implements MonthlyCapitalDao {

    public MonthlyCapitalDaoJpa() {
        super(MonthlyCapital.class);
    }

    @Override
    public Optional<MonthlyCapital> findByUserAndMonth(Long userId, YearMonth month) {
        try (EntityManager em = em()) {
            return em.createQuery(
                    "select mc from MonthlyCapital mc where mc.user.id = :userId and mc.month = :month",
                    MonthlyCapital.class)
                    .setParameter("userId", userId)
                    .setParameter("month", month)
                    .getResultStream()
                    .findFirst();
        }
    }

    @Override
    public List<MonthlyCapital> findByUser(Long userId) {
        try (EntityManager em = em()) {
            return em.createQuery(
                    "select mc from MonthlyCapital mc where mc.user.id = :userId order by mc.month",
                    MonthlyCapital.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

    public MonthlyCapital saveForUser(MonthlyCapital capital, Long userId) {
        try (EntityManager em = em()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                User userRef = em.getReference(User.class, userId);
                capital.setUser(userRef);
                MonthlyCapital managed;
                if (capital.getId() == null) {
                    em.persist(capital);
                    managed = capital;
                } else {
                    managed = em.merge(capital);
                }
                tx.commit();
                return managed;
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }
}

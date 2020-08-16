package com.dkb.bankaccount.repository;

import com.dkb.bankaccount.entity.Transaction;
import com.dkb.bankaccount.entity.TransactionType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class TransactionCustomRepositoryImpl implements TransactionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Transaction> findUserByEmails(LocalDate fromDate, LocalDate toDate, TransactionType transactionType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = cb.createQuery(Transaction.class);
        Root<Transaction> root = query.from(Transaction.class);

        List<Predicate> predicates = new ArrayList<>();

        if(nonNull(fromDate)) {
            predicates.add(cb.lessThanOrEqualTo(root.get("transactionDate"), fromDate));
        }
        if(nonNull(toDate)) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("transactionDate"), toDate));
        }
        if(nonNull(transactionType)) {
            predicates.add(cb.equal(root.get("transactionType"), transactionType));
        }

        query.select(root)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query).getResultList();
    }
}

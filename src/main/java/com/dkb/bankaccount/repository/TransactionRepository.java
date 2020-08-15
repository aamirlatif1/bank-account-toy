package com.dkb.bankaccount.repository;

import com.dkb.bankaccount.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

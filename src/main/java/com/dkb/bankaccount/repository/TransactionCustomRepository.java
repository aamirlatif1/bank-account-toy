package com.dkb.bankaccount.repository;

import com.dkb.bankaccount.entity.Transaction;
import com.dkb.bankaccount.entity.TransactionType;

import java.time.LocalDate;
import java.util.List;

public interface TransactionCustomRepository {
    List<Transaction> findTransactionByDate(Long accountId, LocalDate fromDate,
                                            LocalDate toDate, TransactionType transactionType);
}

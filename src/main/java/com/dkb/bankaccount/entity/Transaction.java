package com.dkb.bankaccount.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    private BigDecimal runningBalance;

    private TransactionType transactionType;

    private Long relatedTransactionId;

    private Long accountId;

    private String details;

    private String reference;

    private LocalDate transactionDate;
}
package com.dkb.bankaccount.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    private BigDecimal runningBalance;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Long relatedTransactionId;

    private Long accountId;

    private String details;

    private String reference;

    private LocalDateTime transactionDate;
}

package com.dkb.bankaccount.dto;

import com.dkb.bankaccount.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long transactionId;
    private Long relatedTransactionId;
    private BigDecimal amount;
    private BigDecimal runningBalance;
    private String reference;
    private String details;
    private TransactionType type;
    private LocalDateTime transactionDate;

}

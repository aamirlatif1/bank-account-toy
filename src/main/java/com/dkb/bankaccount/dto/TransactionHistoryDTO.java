package com.dkb.bankaccount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDTO {
    private String iban;
    private String firstName;
    private String lastName;
    private BigDecimal currentBalance;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    private List<TransactionDTO> transactions;
}

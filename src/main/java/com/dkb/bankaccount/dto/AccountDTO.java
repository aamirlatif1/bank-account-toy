package com.dkb.bankaccount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String iban;
    private String firstName;
    private String lastName;
    private BigDecimal currentBalance;
}

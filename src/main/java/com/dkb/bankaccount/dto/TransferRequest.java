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
public class TransferRequest {

    private BigDecimal amount;
    private String sourceAccount;
    private String destinationAccount;
    private String reference;
    private String details;
}

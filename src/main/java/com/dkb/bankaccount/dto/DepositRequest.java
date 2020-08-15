package com.dkb.bankaccount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {

    @NotBlank
    private String iban;

    @NotNull
    private BigDecimal amount;

    private String reference;

    private String details;

}

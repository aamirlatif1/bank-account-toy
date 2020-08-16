package com.dkb.bankaccount.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BankAccount {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String iban;

    private String accountNumber;

    private AccountStatus accountStatus;

    private BigDecimal currentBalance;

    private BigDecimal overdraftLimit;
}

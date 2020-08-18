package com.dkb.bankaccount.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BANK_ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@SequenceGenerator(name = "account_generator", sequenceName = "account_sequence")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String iban;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private BigDecimal currentBalance;

    private BigDecimal overdraftLimit;
}

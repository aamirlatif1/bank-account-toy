package com.dkb.bankaccount.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
}

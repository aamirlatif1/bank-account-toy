package com.dkb.bankaccount.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String iban) {
        super(String.format("account for iban : %s not exist", iban));
    }
}

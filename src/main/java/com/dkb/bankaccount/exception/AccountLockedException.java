package com.dkb.bankaccount.exception;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException(String iban) {
        super(String.format("account with %s IBAN is locked", iban));
    }
}

package com.dkb.bankaccount.util;


import com.dkb.bankaccount.dto.*;
import com.dkb.bankaccount.entity.AccountStatus;
import com.dkb.bankaccount.entity.BankAccount;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import java.math.BigDecimal;

public final class DataProvider {

    public static final long ACCOUNT_ID = 1234567L;
    public static final String IBAN = "DE64120300000001234567";
    public static final String DEST_IBAN = "DE80120300000007654321";
    public static final String INVALID_IBAN = "DE64120300000001111111";
    public static final String ACCOUNT_NUMBER = "0001234567";
    public static final String BANK_CODE = "12030000";
    public static final String FIRST_NAME = "First";
    public static final String LAST_NAME = "Last";
    public static final String ADDRESS = "Berlin";
    public static final double TRANSACTION_AMOUNT = 105.50;
    public static final String TRANSACTION_DETAILS = "sample transaction";
    public static final String TRANSACTION_REFERENCE = "sample reference";
    public static final double RUNNING_BANLANCE = 205.0;

    public static AccountCreateRequest getAccountCreateRequest() {
        AccountCreateRequest request = new AccountCreateRequest();
        request.setFirstName("First");
        request.setLastName("Last");
        request.setAddress("Berlin");
        return request;
    }

    public static DepositRequest createDepositRequest() {
        return DepositRequest.builder()
                .amount(BigDecimal.valueOf(TRANSACTION_AMOUNT))
                .iban(IBAN)
                .details(TRANSACTION_DETAILS)
                .reference(TRANSACTION_REFERENCE)
                .build();
    }

    public static TransferRequest createTransferRequest() {
        return TransferRequest.builder()
                .amount(BigDecimal.valueOf(TRANSACTION_AMOUNT))
                .details(TRANSACTION_DETAILS)
                .reference(TRANSACTION_REFERENCE)
                .sourceAccount(IBAN)
                .destinationAccount(DEST_IBAN)
                .build();
    }

    public static Iban createIban(long number) {
        return new Iban.Builder()
                .countryCode(CountryCode.DE)
                .bankCode(BANK_CODE)
                .accountNumber(String.format("%010d", number))
                .build();
    }

    public static AccountDTO createAccountDTO() {
        return AccountDTO.builder()
                .iban(IBAN)
                .currentBalance(BigDecimal.ZERO)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static TransactionDTO createTransactionDTO() {
        return TransactionDTO.builder()
                .amount(BigDecimal.valueOf(TRANSACTION_AMOUNT))
                .details(TRANSACTION_DETAILS)
                .reference(TRANSACTION_REFERENCE)
                .runningBalance(BigDecimal.valueOf(RUNNING_BANLANCE))
                .build();
    }

    public static BankAccount createBankAccount(double currentBalance) {
        return BankAccount.builder()
                .id(1234567L)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .overdraftLimit(BigDecimal.ZERO)
                .iban(IBAN)
                .accountStatus(AccountStatus.ACTIVE)
                .accountNumber(ACCOUNT_NUMBER)
                .currentBalance(BigDecimal.valueOf(currentBalance))
                .build();
    }
}

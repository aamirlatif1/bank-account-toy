package com.dkb.bankaccount.util;


import com.dkb.bankaccount.dto.AccountCreateRequest;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

public final class DataProvider {

    public static final String IBAN = "DE1234123412345";
    public static final String BANK_CODE = "12030000";

    public static AccountCreateRequest getAccountCreateRequest(){
        AccountCreateRequest request = new AccountCreateRequest();
        request.setFirstName("First");
        request.setLastName("Last");
        request.setAddress("Berlin");
        return request;
    }

    public static Iban createIban(long number){
        return new Iban.Builder()
                .countryCode(CountryCode.DE)
                .bankCode(BANK_CODE)
                .accountNumber(String.format("%010d", number))
                .build();
    }
}

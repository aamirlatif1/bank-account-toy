package com.dkb.bankaccount.service.impl;

import com.dkb.bankaccount.service.IbanService;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IbanServiceImpl implements IbanService {

    @Value("${com.dkb.bankCode:12030000}")
    private String bankCode;

    @Value("${com.dkb.bankCode:DE}")
    private String countryCode;

    @Override
    public Iban generateIban() {
        return new Iban.Builder()
                .countryCode(CountryCode.getByCode(countryCode))
                .bankCode(bankCode)
                .buildRandom();
    }

    @Override
    public Iban generateIban(long accountNumber) {
        return new Iban.Builder()
                .countryCode(CountryCode.getByCode(countryCode))
                .bankCode(bankCode)
                .accountNumber(String.format("%010d", accountNumber))
                .build();
    }

}

package com.dkb.bankaccount.service;

import org.iban4j.Iban;

public interface IbanService {

    Iban generateIban();

    Iban generateIban(long accountNumber);
}

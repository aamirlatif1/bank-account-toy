package com.dkb.bankaccount.service;

import org.iban4j.Iban;
import org.springframework.stereotype.Service;

public interface IbanService {

    Iban generateIban();

    Iban generateIban(long accountNumber);
}

package com.dkb.bankaccount.service;


import com.dkb.bankaccount.service.impl.IbanServiceImpl;
import com.dkb.bankaccount.util.DataProvider;
import org.iban4j.Iban;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static com.dkb.bankaccount.util.DataProvider.BANK_CODE;
import static com.dkb.bankaccount.util.DataProvider.IBAN;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class IbanServiceTest {

    private IbanService ibanService;

    @BeforeEach
    void setUp() {
        ibanService = new IbanServiceImpl();
        ReflectionTestUtils.setField(ibanService, "bankCode", BANK_CODE);
        ReflectionTestUtils.setField(ibanService, "countryCode", "DE");
    }

    @Test
    public void generateIbanWithGivenAccountNumberSuccess() {
        Iban iban = ibanService.generateIban(1234567L);
        assertThat(iban.toString()).isEqualTo(IBAN);
    }

    @Test
    public void generateRandomIbanSuccess() {
        Iban iban = ibanService.generateIban();
        assertThat(iban.toString().length()).isEqualTo(22);
        assertThat(iban.toString()).containsPattern("DE.*12030000");
    }

}

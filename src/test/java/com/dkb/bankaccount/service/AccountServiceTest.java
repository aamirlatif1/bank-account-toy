package com.dkb.bankaccount.service;

import com.dkb.bankaccount.dto.AccountCreateRequest;
import com.dkb.bankaccount.dto.AccountDTO;
import com.dkb.bankaccount.entity.BankAccount;
import com.dkb.bankaccount.exception.AccountNotFoundException;
import com.dkb.bankaccount.repository.AccountRepository;
import com.dkb.bankaccount.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static com.dkb.bankaccount.util.DataProvider.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private IbanService ibanService;

    private ModelMapper modelMapper;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        accountService = new AccountServiceImpl(accountRepository, ibanService, modelMapper);
    }

    @Test
    public void createAccountSuccess() {
        when(ibanService.generateIban(ACCOUNT_ID))
                .thenReturn(createIban(ACCOUNT_ID));

        when(accountRepository.save(any(BankAccount.class)))
                .thenReturn(createBankAccount(0.0));
        AccountCreateRequest request = getAccountCreateRequest();

        AccountDTO accountDTO = accountService.createAccount(request);

        assertThat(accountDTO.getIban()).isEqualTo(IBAN);
        assertThat(accountDTO.getCurrentBalance()).isEqualTo(BigDecimal.valueOf(0.0));

    }

    @Test
    public void getAccountDetailSuccess() {

        when(accountRepository.findFirstByIban(IBAN))
                .thenReturn(Optional.of(createBankAccount(1000.50)));

        AccountDTO accountDTO = accountService.getAccountDetail(IBAN);

        assertThat(accountDTO.getIban()).containsPattern(IBAN);
        assertThat(accountDTO.getCurrentBalance()).isEqualTo(BigDecimal.valueOf(1000.50));
        assertThat(accountDTO.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(accountDTO.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    public void getInvalidAccount_throwAccountNotFoundException() {
        when(accountRepository.findFirstByIban(INVALID_IBAN))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountDetail(INVALID_IBAN));
    }

}
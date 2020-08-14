package com.dkb.bankaccount.service;

import com.dkb.bankaccount.dto.AccountCreateRequest;
import com.dkb.bankaccount.dto.AccountDTO;
import com.dkb.bankaccount.entity.BankAccount;
import com.dkb.bankaccount.repository.AccountRepository;
import com.dkb.bankaccount.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dkb.bankaccount.util.DataProvider.createIban;
import static com.dkb.bankaccount.util.DataProvider.getAccountCreateRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private IbanService ibanService;

    AccountService accountService;

    @BeforeEach
    void setUp() {
        when(ibanService.generateIban(1))
                .thenReturn(createIban(1));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);

        when(accountRepository.save(any(BankAccount.class)))
                .thenReturn(bankAccount);

        accountService = new AccountServiceImpl(accountRepository, ibanService);
    }

    @Test
    public void createAccountSuccess() {
        AccountCreateRequest request = getAccountCreateRequest();

        AccountDTO accountDTO = accountService.createAccount(request);

        assertThat(accountDTO.getIban()).containsPattern("DE.*12030000");

    }

}
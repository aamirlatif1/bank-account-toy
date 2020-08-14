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
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

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

    private ModelMapper modelMapper;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        when(ibanService.generateIban(1))
                .thenReturn(createIban(1));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrentBalance(BigDecimal.ZERO);
        bankAccount.setId(1L);

        when(accountRepository.save(any(BankAccount.class)))
                .thenReturn(bankAccount);

        modelMapper = new ModelMapper();

        accountService = new AccountServiceImpl(accountRepository, ibanService, modelMapper);
    }

    @Test
    public void createAccountSuccess() {
        AccountCreateRequest request = getAccountCreateRequest();

        AccountDTO accountDTO = accountService.createAccount(request);

        assertThat(accountDTO.getIban()).containsPattern("DE.*12030000");
        assertThat(accountDTO.getCurrentBalance()).isEqualTo(BigDecimal.ZERO);

    }

}
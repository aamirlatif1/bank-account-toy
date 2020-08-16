package com.dkb.bankaccount.service.impl;

import com.dkb.bankaccount.dto.AccountCreateRequest;
import com.dkb.bankaccount.dto.AccountDTO;
import com.dkb.bankaccount.entity.BankAccount;
import com.dkb.bankaccount.exception.AccountNotFoundException;
import com.dkb.bankaccount.repository.AccountRepository;
import com.dkb.bankaccount.service.AccountService;
import com.dkb.bankaccount.service.IbanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.Iban;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final IbanService ibanService;
    private final ModelMapper modelMapper;

    public AccountDTO createAccount(final AccountCreateRequest request) {

        final BankAccount account = new BankAccount();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setAddress(request.getAddress());
        account.setOverdraftLimit(BigDecimal.ZERO);

        final BankAccount newAccount = accountRepository.save(account);

        final Iban iban = ibanService.generateIban(newAccount.getId());
        newAccount.setIban(iban.toString());
        newAccount.setAccountNumber(iban.getAccountNumber());

        accountRepository.save(newAccount);

        return modelMapper.map(newAccount, AccountDTO.class);
    }

    @Override
    public AccountDTO getAccountDetail(final String iban) {
        final BankAccount bankAccount = accountRepository.findFirstByIban(iban)
                .orElseThrow(() -> new AccountNotFoundException(iban));

        return modelMapper.map(bankAccount, AccountDTO.class);
    }
}

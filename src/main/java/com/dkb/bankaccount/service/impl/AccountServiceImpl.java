package com.dkb.bankaccount.service.impl;

import com.dkb.bankaccount.dto.AccountCreateRequest;
import com.dkb.bankaccount.dto.AccountDTO;
import com.dkb.bankaccount.entity.BankAccount;
import com.dkb.bankaccount.repository.AccountRepository;
import com.dkb.bankaccount.service.AccountService;
import com.dkb.bankaccount.service.IbanService;
import lombok.RequiredArgsConstructor;
import org.iban4j.Iban;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final IbanService ibanService;
    private final ModelMapper modelMapper;

    public AccountDTO createAccount(AccountCreateRequest request) {

        BankAccount account = new BankAccount();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setAddress(request.getAddress());

        BankAccount newAccount = accountRepository.save(account);

        Iban iban = ibanService.generateIban(newAccount.getId());


        newAccount.setIban(iban.toString());
        newAccount.setAccountNumber(iban.getAccountNumber());

        accountRepository.save(newAccount);

        return modelMapper.map(newAccount, AccountDTO.class);
    }

    @Override
    public AccountDTO getAccountDetail(String iban) {
        return null;
    }
}

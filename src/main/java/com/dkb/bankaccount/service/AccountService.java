package com.dkb.bankaccount.service;

import com.dkb.bankaccount.dto.AccountCreateRequest;
import com.dkb.bankaccount.dto.AccountDTO;
import org.springframework.stereotype.Service;

public interface AccountService {

    AccountDTO createAccount(AccountCreateRequest request);

    AccountDTO getAccountDetail(String iban);
}

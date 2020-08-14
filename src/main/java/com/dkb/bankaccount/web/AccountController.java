package com.dkb.bankaccount.web;

import com.dkb.bankaccount.dto.AccountDTO;
import com.dkb.bankaccount.dto.AccountCreateRequest;
import com.dkb.bankaccount.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    private AccountDTO createAccount(@RequestBody @Validated AccountCreateRequest request){
        return accountService.createAccount(request);
    }
}

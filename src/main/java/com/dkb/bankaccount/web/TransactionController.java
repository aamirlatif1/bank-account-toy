package com.dkb.bankaccount.web;

import com.dkb.bankaccount.dto.DepositRequest;
import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.dto.TransferRequest;
import com.dkb.bankaccount.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("transactions/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTO depositAmount(@RequestBody @Validated DepositRequest request) {
        return transactionService.depositAmount(request);
    }

    @PostMapping("transactions/transfer")
    public TransactionDTO depositAmount(@RequestBody @Validated TransferRequest request) {
        return transactionService.transferAmount(request);
    }
}

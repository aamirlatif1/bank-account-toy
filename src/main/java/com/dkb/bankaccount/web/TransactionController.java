package com.dkb.bankaccount.web;

import com.dkb.bankaccount.dto.DepositRequest;
import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.dto.TransactionHistoryDTO;
import com.dkb.bankaccount.dto.TransferRequest;
import com.dkb.bankaccount.entity.TransactionType;
import com.dkb.bankaccount.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

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

    @GetMapping("/transactions/history")
    public TransactionHistoryDTO getTransactionHistory(
            @RequestParam(value = "iban") @PastOrPresent @NotEmpty @Length String iban,
            @RequestParam(value = "fromDate", required = false) @PastOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) @PastOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(value = "transactionType", required = false) TransactionType transactionType
    ) {
        return transactionService.searchTransactions(iban, fromDate, toDate, transactionType);
    }
}

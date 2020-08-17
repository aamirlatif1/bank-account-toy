package com.dkb.bankaccount.service;

import com.dkb.bankaccount.dto.DepositRequest;
import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.dto.TransactionHistoryDTO;
import com.dkb.bankaccount.dto.TransferRequest;
import com.dkb.bankaccount.entity.TransactionType;

import java.time.LocalDate;

public interface TransactionService {

    TransactionDTO depositAmount(DepositRequest request);

    TransactionDTO transferAmount(TransferRequest request);

    TransactionHistoryDTO searchTransactions(String iban, LocalDate fromDate,
                                             LocalDate toDate, TransactionType transactionType);
}

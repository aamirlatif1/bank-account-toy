package com.dkb.bankaccount.service;

import com.dkb.bankaccount.dto.DepositRequest;
import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.dto.TransferRequest;

public interface TransactionService {

    TransactionDTO depositAmount(DepositRequest request);

    TransactionDTO transferAmount(TransferRequest request);
}

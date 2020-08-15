package com.dkb.bankaccount.service.impl;

import com.dkb.bankaccount.dto.DepositRequest;
import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.dto.TransferRequest;
import com.dkb.bankaccount.entity.Transaction;
import com.dkb.bankaccount.repository.TransactionRepository;
import com.dkb.bankaccount.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public TransactionDTO depositAmount(DepositRequest request) {
        Transaction transaction = new Transaction();
        transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionDTO.class);
    }

    @Override
    public TransactionDTO transferAmount(TransferRequest request) {
        return null;
    }
}

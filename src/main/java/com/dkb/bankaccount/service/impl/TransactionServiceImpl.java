package com.dkb.bankaccount.service.impl;

import com.dkb.bankaccount.dto.DepositRequest;
import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.dto.TransactionHistoryDTO;
import com.dkb.bankaccount.dto.TransferRequest;
import com.dkb.bankaccount.entity.BankAccount;
import com.dkb.bankaccount.entity.Transaction;
import com.dkb.bankaccount.entity.TransactionType;
import com.dkb.bankaccount.exception.AccountNotFoundException;
import com.dkb.bankaccount.exception.InvalidAmountException;
import com.dkb.bankaccount.repository.AccountRepository;
import com.dkb.bankaccount.repository.TransactionRepository;
import com.dkb.bankaccount.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public TransactionDTO depositAmount(final DepositRequest request) {
        final BankAccount bankAccount = accountRepository.findFirstByIban(request.getIban())
                .orElseThrow(() -> new AccountNotFoundException(request.getIban()));

        final Transaction transaction = Transaction.builder()
                .accountId(bankAccount.getId())
                .transactionType(TransactionType.DEPOSIT)
                .details(request.getDetails())
                .reference(request.getReference())
                .amount(request.getAmount())
                .runningBalance(bankAccount.getCurrentBalance().add(request.getAmount()))
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionDTO.class);
    }

    @Override
    @Transactional
    public TransactionDTO transferAmount(TransferRequest request) {
        BankAccount sourceAccount = accountRepository.findFirstByIban(request.getSourceAccount())
                .orElseThrow(() -> new AccountNotFoundException(request.getSourceAccount()));

        BankAccount destAccount = accountRepository.findFirstByIban(request.getDestinationAccount())
                .orElseThrow(() -> new AccountNotFoundException(request.getDestinationAccount()));

        if (sourceAccount.getCurrentBalance().add(sourceAccount.getOverdraftLimit())
                .subtract(request.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("transaction amount is more than current balance");
        }

        final Transaction transaction = Transaction.builder()
                .accountId(sourceAccount.getId())
                .transactionType(TransactionType.DEPOSIT)
                .details(request.getDetails())
                .reference(request.getReference())
                .amount(request.getAmount())
                .runningBalance(sourceAccount.getCurrentBalance().add(request.getAmount()))
                .transactionDate(LocalDateTime.now())
                .build();

        final Transaction revTransaction = Transaction.builder()
                .accountId(destAccount.getId())
                .transactionType(TransactionType.WITHDRAWAL)
                .details(request.getDetails())
                .reference(request.getReference())
                .amount(request.getAmount())
                .runningBalance(sourceAccount.getCurrentBalance().subtract(request.getAmount()))
                .transactionDate(LocalDateTime.now())
                .build();


        transactionRepository.saveAll(Arrays.asList(transaction, revTransaction));

        transaction.setRelatedTransactionId(revTransaction.getId());
        revTransaction.setRelatedTransactionId(transaction.getId());

        transactionRepository.saveAll(Arrays.asList(transaction, revTransaction));

        return modelMapper.map(revTransaction, TransactionDTO.class);
    }

    @Override
    public TransactionHistoryDTO searchTransactions(String iban, LocalDate fromDate,
                                                    LocalDate toDate, TransactionType transactionType) {

        final BankAccount bankAccount = accountRepository.findFirstByIban(iban)
                .orElseThrow(() -> new AccountNotFoundException(iban));

        final List<Transaction> transactions =  transactionRepository.findTransactionByDate(bankAccount.getId(), fromDate, toDate, transactionType);
        final TransactionHistoryDTO transactionHistory = modelMapper.map(bankAccount, TransactionHistoryDTO.class);

        if(!transactions.isEmpty()) {
            Transaction firstTransaction = transactions.get(0);
            transactionHistory.setOpeningBalance(firstTransaction.getRunningBalance().subtract(firstTransaction.getAmount()));
            transactionHistory.setClosingBalance(transactions.get(transactions.size()-1).getRunningBalance());

            List<TransactionDTO> transactionDTOS = transactions.stream()
                    .map(v -> modelMapper.map(v, TransactionDTO.class))
                    .collect(Collectors.toList());
            transactionHistory.setTransactions(transactionDTOS);
        }

        return transactionHistory;
    }
}

package com.dkb.bankaccount.service;

import com.dkb.bankaccount.dto.DepositRequest;
import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.dto.TransferRequest;
import com.dkb.bankaccount.entity.Transaction;
import com.dkb.bankaccount.entity.TransactionType;
import com.dkb.bankaccount.exception.AccountNotFoundException;
import com.dkb.bankaccount.exception.InvalidAmountException;
import com.dkb.bankaccount.repository.AccountRepository;
import com.dkb.bankaccount.repository.TransactionRepository;
import com.dkb.bankaccount.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.dkb.bankaccount.util.DataProvider.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl(transactionRepository, accountRepository, new ModelMapper());
    }

    @Test
    public void depositAmountSuccess() {
        double currentBalance = 1000.50;
        when(accountRepository.findFirstByIban(IBAN))
                .thenReturn(Optional.of(createBankAccount(currentBalance)));

        DepositRequest request = createDepositRequest();

        TransactionDTO transactionDTO = transactionService.depositAmount(request);

        verify(transactionRepository, atLeastOnce()).save(any(Transaction.class));
        assertThat(transactionDTO.getAmount()).isEqualTo(BigDecimal.valueOf(TRANSACTION_AMOUNT));
        assertThat(transactionDTO.getRunningBalance()).isEqualTo(BigDecimal.valueOf(TRANSACTION_AMOUNT+currentBalance));
        assertThat(transactionDTO.getDetails()).isEqualTo(TRANSACTION_DETAILS);
        assertThat(transactionDTO.getReference()).isEqualTo(TRANSACTION_REFERENCE);
        assertThat(transactionDTO.getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(transactionDTO.getTransactionDate()).isNotNull();
    }

    @Test
    public void getInvalidIbanForTransaction_throwAccountNotFoundException() {
        when(accountRepository.findFirstByIban(INVALID_IBAN))
                .thenReturn(Optional.empty());

        DepositRequest request = createDepositRequest();
        request.setIban(INVALID_IBAN);

        assertThrows(AccountNotFoundException.class, () -> transactionService.depositAmount(request));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void transferAmountSuccess() {

        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            List<Transaction> transactions = (List<Transaction>)arg;
            if(transactions.get(0).getId() == null){
                transactions.get(0).setId(1L);
            }
            if(transactions.get(1).getId() == null){
                transactions.get(1).setId(2L);
            }
            return null;
        }).when(transactionRepository).saveAll(any(List.class));

        double currentBalance = 1000.50;
        when(accountRepository.findFirstByIban(IBAN))
                .thenReturn(Optional.of(createBankAccount(currentBalance)));

        when(accountRepository.findFirstByIban(DEST_IBAN))
                .thenReturn(Optional.of(createBankAccount(currentBalance)));

        TransferRequest request = createTransferRequest();

        TransactionDTO transactionDTO = transactionService.transferAmount(request);

        verify(transactionRepository, times(2)).saveAll(any(List.class));
        assertThat(transactionDTO.getAmount()).isEqualTo(BigDecimal.valueOf(TRANSACTION_AMOUNT));
        assertThat(transactionDTO.getRunningBalance()).isEqualTo(BigDecimal.valueOf(currentBalance-TRANSACTION_AMOUNT));
        assertThat(transactionDTO.getDetails()).isEqualTo(TRANSACTION_DETAILS);
        assertThat(transactionDTO.getReference()).isEqualTo(TRANSACTION_REFERENCE);
        assertThat(transactionDTO.getType()).isEqualTo(TransactionType.WITHDRAWAL);
        assertThat(transactionDTO.getTransactionDate()).isNotNull();
        assertThat(transactionDTO.getRelatedTransactionId()).isEqualTo(1);
    }

    @Test
    public void transferInvalidAmount_throwAccountNotFoundException() {
        when(accountRepository.findFirstByIban(DEST_IBAN))
                .thenReturn(Optional.of(createBankAccount(100)));
        when(accountRepository.findFirstByIban(IBAN))
                .thenReturn(Optional.of(createBankAccount(20)));

        TransferRequest request = createTransferRequest();

        assertThrows(InvalidAmountException.class, () -> transactionService.transferAmount(request));
    }
}

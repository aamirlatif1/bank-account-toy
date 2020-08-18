package com.dkb.bankaccount;

import com.dkb.bankaccount.dto.AccountCreateRequest;
import com.dkb.bankaccount.dto.AccountDTO;
import com.dkb.bankaccount.dto.TransactionDTO;
import com.dkb.bankaccount.dto.TransactionHistoryDTO;
import com.dkb.bankaccount.entity.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.dkb.bankaccount.util.DataProvider.IBAN;
import static com.dkb.bankaccount.util.DataProvider.getAccountCreateRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BankAccountToyApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testTransactionHistory() throws Exception {
        ResponseEntity<TransactionHistoryDTO> response =
                restTemplate.getForEntity("/transactions/history?iban="+IBAN+"&transactionType=WITHDRAWAL&fromDate=2020-08-08&toDate=2020-08-10",
                        TransactionHistoryDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TransactionHistoryDTO history = response.getBody();
        assertThat(history.getIban()).isEqualTo(IBAN);
        assertThat(history.getCurrentBalance()).isEqualTo(BigDecimal.valueOf(1000.0));
        assertThat(history.getCurrentBalance()).isEqualTo(BigDecimal.valueOf(1000.0));
        assertThat(history.getTransactions().size()).isEqualTo(1);
        TransactionDTO transaction = history.getTransactions().get(0);
        assertThat(transaction.getTransactionId()).isEqualTo(2);
        assertThat(transaction.getRelatedTransactionId()).isEqualTo(3);
        assertThat(transaction.getType()).isEqualTo(TransactionType.WITHDRAWAL);
    }

    @Test
    public void createAccountSuccess() throws Exception {
        AccountCreateRequest accountCreateRequest = getAccountCreateRequest();
        HttpEntity<AccountCreateRequest> request = new HttpEntity<>(accountCreateRequest);
        ResponseEntity<AccountDTO> response =
                restTemplate.postForEntity("/accounts", request, AccountDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getIban()).containsPattern("DE.*12030000");

    }

}

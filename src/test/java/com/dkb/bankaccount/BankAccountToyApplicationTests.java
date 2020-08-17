package com.dkb.bankaccount;

import com.dkb.bankaccount.dto.TransactionHistoryDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.dkb.bankaccount.util.DataProvider.IBAN;
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

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TransactionHistoryDTO history = response.getBody();
        Assertions.assertThat(history.getCurrentBalance()).isEqualTo(BigDecimal.valueOf(1000.0));
        Assertions.assertThat(history.getCurrentBalance()).isEqualTo(BigDecimal.valueOf(1000.0));
        Assertions.assertThat(history.getTransactions().size()).isEqualTo(1);
    }

}

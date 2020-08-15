package com.dkb.bankaccount.web;

import com.dkb.bankaccount.dto.DepositRequest;
import com.dkb.bankaccount.dto.TransferRequest;
import com.dkb.bankaccount.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.dkb.bankaccount.util.DataProvider.*;
import static com.dkb.bankaccount.util.JsonUtil.toJson;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void depositAmountSuccess() throws Exception {
        DepositRequest request = createDepositRequest();

        given(transactionService.depositAmount(request))
                .willReturn(createTransactionDTO());

        mockMvc.perform(post("/transactions/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("amount", equalTo(TRANSACTION_AMOUNT)))
                .andExpect(jsonPath("runningBalance", equalTo(RUNNING_BANLANCE)))
                .andExpect(jsonPath("details", equalTo(TRANSACTION_DETAILS)))
                .andExpect(jsonPath("reference", equalTo(TRANSACTION_REFERENCE)));
    }

    @Test
    public void transferAmountSuccess() throws Exception {
        TransferRequest request = createTransferRequest();

        given(transactionService.transferAmount(request))
                .willReturn(createTransactionDTO());

        mockMvc.perform(post("/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount", equalTo(TRANSACTION_AMOUNT)))
                .andExpect(jsonPath("runningBalance", equalTo(RUNNING_BANLANCE)))
                .andExpect(jsonPath("details", equalTo(TRANSACTION_DETAILS)))
                .andExpect(jsonPath("reference", equalTo(TRANSACTION_REFERENCE)));
    }
}

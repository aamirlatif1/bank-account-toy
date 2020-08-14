package com.dkb.bankaccount.web;

import com.dkb.bankaccount.dto.AccountCreateRequest;
import com.dkb.bankaccount.service.AccountService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;


    @Test
    public void createAccount() throws Exception {
        AccountCreateRequest request = getAccountCreateRequest();

        given(accountService.createAccount(request))
                .willReturn(createAccountDTO());

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("iban", equalTo(IBAN)))
                .andExpect(jsonPath("firstName", equalTo("First")))
                .andExpect(jsonPath("lastName", equalTo("Last")))
                .andExpect(jsonPath("currentBalance", equalTo(0)));
    }

    @Test
    public void getAccountDetailSuccess() throws Exception {

        given(accountService.getAccountDetail(IBAN))
                .willReturn(createAccountDTO());

        mockMvc.perform(get("/accounts/iban/"+IBAN)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("iban", equalTo(IBAN)))
                .andExpect(jsonPath("firstName", equalTo("First")))
                .andExpect(jsonPath("lastName", equalTo("Last")))
                .andExpect(jsonPath("currentBalance", equalTo(0)));
    }

}

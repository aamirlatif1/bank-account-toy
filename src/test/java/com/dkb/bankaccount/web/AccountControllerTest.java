package com.dkb.bankaccount.web;

import com.dkb.bankaccount.dto.AccountDTO;
import com.dkb.bankaccount.dto.CreateAccountRequest;
import com.dkb.bankaccount.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class AccountControllerTest {

    public static final String IBAN = "DE1234123412345";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;


    @Test
    public void createAccount() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setFirstName("First");
        request.setLastName("Last");
        request.setAddress("Berlin");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setIban(IBAN);

        BDDMockito.given(accountService.createAccount(request)).willReturn(accountDTO);

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("iban", equalTo(IBAN)));
    }

    public String toJson(Object anObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(anObject);
    }


}

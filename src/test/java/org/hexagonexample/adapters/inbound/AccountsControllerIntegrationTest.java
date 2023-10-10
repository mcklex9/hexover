package org.hexagonexample.adapters.inbound;

import org.hexagonexample.application.AccountsFacade;
import org.hexagonexample.application.ports.inbound.AccountDto;
import org.hexagonexample.application.ports.inbound.CreateAccountCommand;
import org.hexagonexample.application.ports.inbound.GetAccountsCommand;
import org.hexagonexample.domain.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountsController.class)
class AccountsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountsFacade accountsFacade;

    private static String createAccountJsonContent(String accountName) {
        return """
                {
                    "name": "%s"
                }
                """.formatted(accountName);
    }

    @Test
    public void shouldCreateAccount() throws Exception {
        String accountName = "accountName";
        String createAccountContentJson = createAccountJsonContent(accountName);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createAccountContentJson))
                .andExpect(status().isCreated());

        verify(accountsFacade).handle(new CreateAccountCommand(accountName));
    }

    @Test
    public void shouldNotCreateAccountWhenBadPayload() throws Exception {
        String badRequestContent = "12";

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequestContent))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(accountsFacade);
    }

    @Test
    public void shouldReturnAllAccounts() throws Exception {
        AccountDto account1 = account(BigDecimal.ONE);
        AccountDto account2 = account(BigDecimal.ZERO);
        when(accountsFacade.handle(any(GetAccountsCommand.class))).thenReturn(List.of(account1, account2));

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts", hasSize(2)))
                .andExpect(jsonPath("$.accounts.[0].accountId.id", equalTo(account1.accountId().getId().toString())))
                .andExpect(jsonPath("$.accounts.[1].accountId.id", equalTo(account2.accountId().getId().toString())));
    }

    private AccountDto account(BigDecimal limit) {
        return new AccountDto(AccountId.generate(), limit);
    }
}
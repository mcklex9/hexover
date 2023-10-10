package org.hexagonexample.adapters.inbound;

import org.hexagonexample.application.AccountsFacade;
import org.hexagonexample.application.ports.inbound.CreateAccountCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk());
    }

}
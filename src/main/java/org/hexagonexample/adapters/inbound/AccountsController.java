package org.hexagonexample.adapters.inbound;

import lombok.RequiredArgsConstructor;
import org.hexagonexample.application.AccountsFacade;
import org.hexagonexample.application.ports.inbound.AccountDto;
import org.hexagonexample.application.ports.inbound.CreateAccountCommand;
import org.hexagonexample.application.ports.inbound.GetAccountsCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
class AccountsController {

    private final AccountsFacade accountsFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createAccount(@RequestBody CreateAccountRequest request) {
        CreateAccountCommand command = new CreateAccountCommand(request.name());
        accountsFacade.handle(command);
    }

    @GetMapping
    GetAccountsResponse getAccounts() {
        List<AccountDto> accounts = accountsFacade.handle(new GetAccountsCommand());
        return new GetAccountsResponse(accounts);

    }
}

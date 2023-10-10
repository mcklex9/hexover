package org.hexagonexample.adapters.inbound;

import lombok.RequiredArgsConstructor;
import org.hexagonexample.application.AccountsFacade;
import org.hexagonexample.application.ports.inbound.CreateAccountCommand;
import org.hexagonexample.application.ports.inbound.GetAccountsCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    String getAccounts() {
        return accountsFacade.handle(new GetAccountsCommand()).toString();

    }
}

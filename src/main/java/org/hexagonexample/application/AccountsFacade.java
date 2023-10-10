package org.hexagonexample.application;

import org.hexagonexample.application.ports.inbound.*;
import org.hexagonexample.application.ports.outbound.AccountsRepository;
import org.hexagonexample.application.ports.outbound.FraudCheckVerificationService;
import org.hexagonexample.application.ports.outbound.SoftCheckVerificationService;
import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountsFacade {

    private final AccountsService accountsService;

    public AccountsFacade(AccountsRepository accountsRepository, FraudCheckVerificationService fraudCheckVerificationService, SoftCheckVerificationService accountVerificationService) {
        accountsService = new AccountsService(accountsRepository, fraudCheckVerificationService, accountVerificationService);
    }

    public AccountId handle(CreateAccountCommand command) {
        return accountsService.createAccount(command);
    }

    public void handle(ApproveFraudCheckCommand command) {
        accountsService.approveFraudCheckAccount(command);
    }

    public void handle(RejectFraudCheckCommand command) {
        accountsService.rejectFraudCheckAccount(command);
    }

    public void handle(ApproveSoftCheckCommand command) {
        accountsService.approveSoftCheckAccount(command);
    }

    public void handle(RejectSoftCheckCommand command) {
        accountsService.rejectSoftCheckAccount(command);
    }

    public List<Account> handle(GetAccountsCommand command) {
        return accountsService.getAccounts();
    }
}

package org.hexagonexample.adapters.inbound;

import io.cucumber.java8.En;
import org.hexagonexample.adapters.outbound.InMemoryAccountVerificationService;
import org.hexagonexample.adapters.outbound.InMemoryFraudCheckVerificationService;
import org.hexagonexample.application.TestAccountsFacade;
import org.hexagonexample.application.ports.inbound.*;
import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountsSteps implements En {

    private AccountId accountId;

    public AccountsSteps(TestAccountsFacade testAccountsFacade) {
        When("account created by user", () -> {
            CreateAccountCommand command = new CreateAccountCommand("");
            accountId = testAccountsFacade.handle(command);
        });
        When("account passed fraud check", () -> {
            testAccountsFacade.handle(new ApproveFraudCheckCommand(accountId));
        });
        When("account failed fraud check", () -> {
            testAccountsFacade.handle(new RejectFraudCheckCommand(accountId));
        });
        When("account passed soft check", () -> {
            testAccountsFacade.handle(new ApproveSoftCheckCommand(accountId));
        });
        When("account failed soft check", () -> {
            testAccountsFacade.handle(new RejectSoftCheckCommand(accountId));
        });

        Then("account is under fraud check", () -> {
                    InMemoryFraudCheckVerificationService inMemoryFraudCheckVerificationService = testAccountsFacade.getInMemoryFraudCheckVerificationService();
                    inMemoryFraudCheckVerificationService.verifyAccountSentForFraudCheck(accountId);
                }
        );
        Then("account is under soft check", () -> {
                    InMemoryAccountVerificationService accountVerificationService = testAccountsFacade.getAccountVerificationService();
                    accountVerificationService.verifyAccountSentForSoftCheck(accountId);
                }
        );
        Then("account is approved and has positive limit", () -> {
                    Account account = testAccountsFacade.getInMemoryAccountsRepository().loadBy(accountId);
                    assertEquals(BigDecimal.valueOf(200), account.getLimit());
                }
        );
        Then("account is rejected and limit is zero", () -> {
                    Account account = testAccountsFacade.getInMemoryAccountsRepository().loadBy(accountId);
                    assertTrue(account.isRejected());
                    assertEquals(BigDecimal.ZERO, account.getLimit());
                }
        );
    }
}

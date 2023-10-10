package org.hexagonexample.adapters.outbound;

import org.hexagonexample.application.ports.outbound.FraudCheckVerificationService;
import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertTrue;

public class InMemoryFraudCheckVerificationService implements FraudCheckVerificationService {

    private final ConcurrentHashMap<AccountId, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public void fraudCheckAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    public void verifyAccountSentForFraudCheck(AccountId accountId) {
        assertTrue(accounts.containsKey(accountId));
    }
}

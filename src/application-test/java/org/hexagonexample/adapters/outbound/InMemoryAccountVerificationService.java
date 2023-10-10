package org.hexagonexample.adapters.outbound;

import org.hexagonexample.application.ports.outbound.SoftCheckVerificationService;
import org.hexagonexample.domain.AccountId;

import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.Assert.assertTrue;

public class InMemoryAccountVerificationService implements SoftCheckVerificationService {

    private final CopyOnWriteArraySet<AccountId> accounts = new CopyOnWriteArraySet<>();

    @Override
    public void softCheckAccount(AccountId accountId) {
        accounts.add(accountId);
    }

    public void verifyAccountSentForSoftCheck(AccountId accountId) {
        assertTrue(accounts.contains(accountId));
    }
}

package org.hexagonexample.application.ports.outbound;

import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(AccountId accountId) {
        super("Account not found fo id: "+accountId);
    }
}

package org.hexagonexample.application.ports.outbound;

import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;

public interface SoftCheckVerificationService {

    void softCheckAccount(AccountId accountId);
}

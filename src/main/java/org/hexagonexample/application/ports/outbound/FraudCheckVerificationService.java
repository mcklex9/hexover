package org.hexagonexample.application.ports.outbound;

import org.hexagonexample.domain.Account;

public interface FraudCheckVerificationService {

    void fraudCheckAccount(Account account);
}

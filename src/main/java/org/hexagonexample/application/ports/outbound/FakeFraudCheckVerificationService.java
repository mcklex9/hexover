package org.hexagonexample.application.ports.outbound;

import org.hexagonexample.domain.Account;
import org.springframework.stereotype.Service;

@Service
public class FakeFraudCheckVerificationService implements FraudCheckVerificationService {
    @Override
    public void fraudCheckAccount(Account account) {
    }
}

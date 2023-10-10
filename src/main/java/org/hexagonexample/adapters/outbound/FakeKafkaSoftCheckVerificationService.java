package org.hexagonexample.adapters.outbound;

import org.hexagonexample.application.ports.outbound.SoftCheckVerificationService;
import org.hexagonexample.domain.AccountId;

class FakeKafkaSoftCheckVerificationService implements SoftCheckVerificationService {
    @Override
    public void softCheckAccount(AccountId accountId) {
        // add implementation
    }
}

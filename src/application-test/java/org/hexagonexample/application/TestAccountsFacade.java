package org.hexagonexample.application;

import lombok.Getter;
import org.hexagonexample.adapters.outbound.InMemoryAccountVerificationService;
import org.hexagonexample.adapters.outbound.InMemoryAccountsRepository;
import org.hexagonexample.adapters.outbound.InMemoryFraudCheckVerificationService;

@Getter
public class TestAccountsFacade extends AccountsFacade {

    private final InMemoryAccountsRepository inMemoryAccountsRepository;
    private final InMemoryAccountVerificationService accountVerificationService;
    private final InMemoryFraudCheckVerificationService inMemoryFraudCheckVerificationService;


    public TestAccountsFacade() {
        this(new InMemoryAccountsRepository(), new InMemoryFraudCheckVerificationService(), new InMemoryAccountVerificationService());
    }

    public TestAccountsFacade(InMemoryAccountsRepository inMemoryAccountsRepository, InMemoryFraudCheckVerificationService inMemoryFraudCheckVerificationService, InMemoryAccountVerificationService inMemoryAccountVerificationService) {
        super(inMemoryAccountsRepository, inMemoryFraudCheckVerificationService, inMemoryAccountVerificationService);
        this.inMemoryAccountsRepository = inMemoryAccountsRepository;
        this.inMemoryFraudCheckVerificationService = inMemoryFraudCheckVerificationService;
        this.accountVerificationService = inMemoryAccountVerificationService;
    }
}

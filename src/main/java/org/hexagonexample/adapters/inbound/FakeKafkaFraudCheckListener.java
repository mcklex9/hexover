package org.hexagonexample.adapters.inbound;

import lombok.RequiredArgsConstructor;
import org.hexagonexample.application.AccountsFacade;
import org.hexagonexample.application.ports.inbound.ApproveFraudCheckCommand;
import org.hexagonexample.domain.AccountId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fraudchecks")
@RequiredArgsConstructor
// This class is for checking purpose. In real case this might be Kafka listener or any other async listener
// which listens on verification results
class FakeKafkaFraudCheckListener {
    private final AccountsFacade accountsFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createAccount(@RequestBody ApproveFraudCheckRequest request) {
        ApproveFraudCheckCommand command = new ApproveFraudCheckCommand(request.accountId());
        accountsFacade.handle(command);
    }

    private record ApproveFraudCheckRequest(AccountId accountId) {

    }
}

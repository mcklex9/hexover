package org.hexagonexample.adapters.inbound;

import lombok.RequiredArgsConstructor;
import org.hexagonexample.application.AccountsFacade;
import org.hexagonexample.application.ports.inbound.ApproveSoftCheckCommand;
import org.hexagonexample.domain.AccountId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("softchecks")
@RequiredArgsConstructor
// This class is for checking purpose. In real case this might be Kafka listener or any other async listener
// which listens on verification results
class FakeKafkaSoftCheckListener {

    private final AccountsFacade accountsFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createAccount(@RequestBody ApproveSoftCheckRequest request) {
        ApproveSoftCheckCommand command = new ApproveSoftCheckCommand(request.accountId());
        accountsFacade.handle(command);
    }

    private static record ApproveSoftCheckRequest(AccountId accountId) {

    }


}

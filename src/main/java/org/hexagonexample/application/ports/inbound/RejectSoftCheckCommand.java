package org.hexagonexample.application.ports.inbound;

import org.hexagonexample.domain.AccountId;

public record RejectSoftCheckCommand(AccountId accountId) {
}

package org.hexagonexample.application.ports.inbound;

import org.hexagonexample.domain.AccountId;

public record SuspendAccountCommand(AccountId accountId) {
}

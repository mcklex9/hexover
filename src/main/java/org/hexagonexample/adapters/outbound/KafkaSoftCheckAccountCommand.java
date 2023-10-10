package org.hexagonexample.adapters.outbound;

import org.hexagonexample.domain.AccountId;

record KafkaSoftCheckAccountCommand(AccountId accountId) {
}

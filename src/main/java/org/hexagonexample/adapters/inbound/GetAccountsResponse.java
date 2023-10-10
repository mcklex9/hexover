package org.hexagonexample.adapters.inbound;

import org.hexagonexample.application.ports.inbound.AccountDto;

import java.util.List;

record GetAccountsResponse(List<AccountDto> accounts) {
}

package org.hexagonexample.application.ports.inbound;

import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;

import java.math.BigDecimal;

public record AccountDto(AccountId accountId, BigDecimal limit) {

    public static AccountDto from(Account account) {
        AccountId id = account.getId();
        BigDecimal limit = account.getLimit();
        return new AccountDto(id, limit);
    }

}
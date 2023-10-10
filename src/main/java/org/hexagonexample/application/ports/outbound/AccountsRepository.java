package org.hexagonexample.application.ports.outbound;

import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;

import java.util.List;
import java.util.function.Consumer;

public interface AccountsRepository {
    Account persist(Account account);

    void update(AccountId id, Consumer<Account> action);

    Account loadBy(AccountId accountId);

    List<Account> getAll();
}

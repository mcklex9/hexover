package org.hexagonexample.adapters.outbound;

import org.hexagonexample.application.ports.outbound.AccountsRepository;
import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class InMemoryAccountsRepository implements AccountsRepository {

    private final ConcurrentHashMap<AccountId, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account persist(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public void update(AccountId id, Consumer<Account> action) {
        Account account = loadBy(id);
        action.accept(account);
        persist(account);
    }

    @Override
    public Account loadBy(AccountId accountId) {
        return accounts.get(accountId);
    }

    @Override
    public List<Account> getAll() {
        return accounts.values().stream().toList();
    }
}

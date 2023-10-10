package org.hexagonexample.adapters.outbound;

import org.hexagonexample.application.ports.outbound.AccountNotFoundException;
import org.hexagonexample.application.ports.outbound.AccountsRepository;
import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.function.Consumer;

public interface MongoAccountsRepository extends AccountsRepository, MongoRepository<Account, AccountId> {

    @Override
    default Account persist(Account account) {
        return save(account);
    }

    @Override
    default void update(AccountId id, Consumer<Account> action) {
        Account account = loadBy(id);
        action.accept(account);
        persist(account);
    }

    default Account loadBy(AccountId accountId) {
        return this.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    default List<Account> getAll() {
        return this.findAll();
    }
}

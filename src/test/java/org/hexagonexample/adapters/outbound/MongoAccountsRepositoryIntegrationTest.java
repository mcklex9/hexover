package org.hexagonexample.adapters.outbound;

import org.hexagonexample.adapters.outbound.common.MongoRepositoryTest;
import org.hexagonexample.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MongoAccountsRepositoryIntegrationTest extends MongoRepositoryTest {

    @Autowired
    MongoAccountsRepository repository;

    @Test
    public void shouldPersistNewAccount() {
        Account account = Account.createAccount("anyname");
        repository.persist(account);

        Account returnedAccount = repository.loadBy(account.getId());

        assertEquals(account, returnedAccount);
    }

    @Test
    public void shouldReturnAllAccounts() {
        Account account1 = Account.createAccount("anyname");
        repository.persist(account1);
        Account account2 = Account.createAccount("anyname");
        repository.persist(account2);


        List<Account> returnedAccounts = repository.getAll();

        assertThat(returnedAccounts).containsExactly(account1, account2);
    }

}
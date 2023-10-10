package org.hexagonexample.application;

import lombok.RequiredArgsConstructor;
import org.hexagonexample.application.ports.inbound.*;
import org.hexagonexample.application.ports.outbound.AccountsRepository;
import org.hexagonexample.application.ports.outbound.FraudCheckVerificationService;
import org.hexagonexample.application.ports.outbound.SoftCheckVerificationService;
import org.hexagonexample.domain.Account;
import org.hexagonexample.domain.AccountId;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
class AccountsService {

    private final AccountsRepository accountsRepository;

    private final FraudCheckVerificationService fraudCheckVerificationService;

    private final SoftCheckVerificationService softCheckVerificationService;


    AccountId createAccount(CreateAccountCommand command) {
        Account account = Account.createAccount(command.name());
        accountsRepository.persist(account);
        startFraudCheckAccount(new StartFraudCheckCommand(account.getId()));
        return account.getId();
    }

    void startFraudCheckAccount(StartFraudCheckCommand command) {
        onAccountDo(command.accountId(), account -> {
            fraudCheckVerificationService.fraudCheckAccount(account);
            accountsRepository.update(account.getId(), Account::startFraudCheck);
        });
    }

    void approveFraudCheckAccount(ApproveFraudCheckCommand command) {
        onAccountDo(command.accountId(), account -> {
            accountsRepository.update(command.accountId(), Account::approveFraudCheck);
            startSoftCheckAccount(new StartSoftCheckCommand(command.accountId()));
        });
    }

    void rejectFraudCheckAccount(RejectFraudCheckCommand command) {
        accountsRepository.update(command.accountId(), Account::rejectFraudCheck);
    }

    void startSoftCheckAccount(StartSoftCheckCommand command) {
        onAccountDo(command.accountId(), account -> {
            softCheckVerificationService.softCheckAccount(command.accountId());
            accountsRepository.update(command.accountId(), Account::startSoftCheck);
        });
    }

    void approveSoftCheckAccount(ApproveSoftCheckCommand command) {
        accountsRepository.update(command.accountId(), Account::approveSoftCheck);
        approveAccount(new ApproveAccountCommand(command.accountId()));
    }

    void rejectSoftCheckAccount(RejectSoftCheckCommand command) {
        accountsRepository.update(command.accountId(), Account::rejectSoftCheck);
    }

    void approveAccount(ApproveAccountCommand command) {
        accountsRepository.update(command.accountId(), Account::approveAccount);
    }

    List<AccountDto> getAccounts() {
        return accountsRepository.getAll().stream().map(AccountDto::from).toList();
    }

    private void onAccountDo(AccountId accountId, Consumer<Account> action) {
        Account account = accountsRepository.loadBy(accountId);
        action.accept(account);
    }

}

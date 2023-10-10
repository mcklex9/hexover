package org.hexagonexample.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;
import java.util.Set;

import static org.hexagonexample.domain.AccountVerificationStatus.*;

@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Account {

    private static final BigDecimal ZERO_LIMIT = BigDecimal.ZERO;
    private static final BigDecimal VERIFIED_LIMIT = BigDecimal.valueOf(200);
    private static final Set<AccountVerificationStatus> PENDING_VERIFICATION_STATUSES = Set.of(FRAUD_CHECK_PENDING, FRAUD_CHECK_APPROVED, SOFT_CHECK_PENDING, SOFT_CHECK_APPROVED);
    private static final Set<AccountVerificationStatus> REJECTED_VERIFICATION_STATUSES = Set.of(FRAUD_CHECK_REJECTED, SOFT_CHECK_REJECTED);

    @Getter
    private AccountId id;

    private String name;

    @Getter
    private BigDecimal limit;

    private AccountVerificationStatus verificationStatus;

    @Version
    private Long version;

    private Account(AccountId accountId, String name, BigDecimal limit) {
        this.id = accountId;
        this.name = name;
        this.limit = limit;
        this.verificationStatus = NOT_VERIFIED;
    }

    public static Account createAccount(String name) {
        AccountId id = AccountId.generate();
        return new Account(id, name, ZERO_LIMIT);
    }

    public void startFraudCheck() {
        if (isNotVerified()) {
            verificationStatus = AccountVerificationStatus.FRAUD_CHECK_PENDING;
        }
    }

    public void approveFraudCheck() {
        if (isFraudCheckPending()) {
            verificationStatus = AccountVerificationStatus.FRAUD_CHECK_APPROVED;
        }
    }

    public void rejectFraudCheck() {
        if (isFraudCheckPending()) {
            verificationStatus = AccountVerificationStatus.FRAUD_CHECK_REJECTED;
        }
    }

    public void startSoftCheck() {
        if (isFraudCheckApproved()) {
            verificationStatus = AccountVerificationStatus.SOFT_CHECK_PENDING;
        }
    }

    public void approveSoftCheck() {
        if (isSoftCheckPending()) {
            verificationStatus = AccountVerificationStatus.SOFT_CHECK_APPROVED;
        }
    }

    public void rejectSoftCheck() {
        if (isSoftCheckPending()) {
            verificationStatus = AccountVerificationStatus.SOFT_CHECK_REJECTED;
        }
    }

    public void approveAccount() {
        if (isSoftCheckApproved()) {
            verificationStatus = AccountVerificationStatus.APPROVED;
            changeLimit(VERIFIED_LIMIT);
        }
    }

    public boolean isRejected() {
        return REJECTED_VERIFICATION_STATUSES.contains(verificationStatus);
    }

    boolean isNotVerified() {
        return NOT_VERIFIED.equals(verificationStatus);
    }

    boolean isFraudCheckPending() {
        return FRAUD_CHECK_PENDING.equals(verificationStatus);
    }

    boolean isFraudCheckApproved() {
        return FRAUD_CHECK_APPROVED.equals(verificationStatus);
    }

    boolean isSoftCheckPending() {
        return SOFT_CHECK_PENDING.equals(verificationStatus);
    }

    boolean isSoftCheckApproved() {
        return SOFT_CHECK_APPROVED.equals(verificationStatus);
    }

    boolean isApproved() {
        return APPROVED.equals(verificationStatus);
    }

    private void changeLimit(BigDecimal newLimit) {
        limit = newLimit;
    }

}

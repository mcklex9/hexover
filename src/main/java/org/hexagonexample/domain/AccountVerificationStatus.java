package org.hexagonexample.domain;

public enum AccountVerificationStatus {
    NOT_VERIFIED,
    FRAUD_CHECK_PENDING,
    FRAUD_CHECK_APPROVED,
    FRAUD_CHECK_REJECTED,
    SOFT_CHECK_PENDING,
    SOFT_CHECK_APPROVED,
    SOFT_CHECK_REJECTED,
    APPROVED,
    SUSPENDED;

}

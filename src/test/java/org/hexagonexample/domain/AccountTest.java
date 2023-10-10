package org.hexagonexample.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    public static final String ACCOUNT_NAME = "accountName";

    @Test
    public void shouldCreateNotVerifiedAccount() {

        Account account = Account.createAccount(ACCOUNT_NAME);

        assertTrue(account.isNotVerified());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldStartFraudCheck() {
        Account account = notVerifiedAccount();

        account.startFraudCheck();

        assertTrue(account.isFraudCheckPending());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldApprovePendingFraudCheck() {
        Account account = notVerifiedAccount();
        account.startFraudCheck();

        account.approveFraudCheck();

        assertTrue(account.isFraudCheckApproved());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldNotApproveNotPendingFraudCheck() {
        Account account = notVerifiedAccount();

        account.approveFraudCheck();

        assertTrue(account.isNotVerified());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldRejectPendingFraudCheck() {
        Account account = notVerifiedAccount();
        account.startFraudCheck();

        account.rejectFraudCheck();

        assertTrue(account.isRejected());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldNotRejectNotPendingFraudCheck() {
        Account account = notVerifiedAccount();

        account.rejectFraudCheck();

        assertTrue(account.isNotVerified());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldStartSoftCheckWhenFraudCheckAlreadyApproved() {
        Account account = approvedFraudCheckAccount();

        account.startSoftCheck();

        assertTrue(account.isSoftCheckPending());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldApprovePendingSoftCheck() {
        Account account = approvedFraudCheckAccount();
        account.startSoftCheck();

        account.approveSoftCheck();

        assertTrue(account.isSoftCheckApproved());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldNotApproveNotPendingSoftCheck() {
        Account account = approvedFraudCheckAccount();

        account.approveSoftCheck();

        assertTrue(account.isFraudCheckApproved());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldRejectPendingSoftCheck() {
        Account account = approvedFraudCheckAccount();
        account.startSoftCheck();

        account.rejectSoftCheck();

        assertTrue(account.isRejected());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldNotRejectNotPendingSoftCheck() {
        Account account = approvedFraudCheckAccount();

        account.rejectSoftCheck();

        assertTrue(account.isFraudCheckApproved());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    @Test
    public void shouldApproveAccountWhenSoftCheckApproved() {
        Account account = approvedSoftCheckAccount();

        account.approveAccount();

        assertTrue(account.isApproved());
        assertEquals(BigDecimal.valueOf(200), account.getLimit());
    }

    @Test
    public void shouldNotApproveAccountWhenSoftCheckNotApproved() {
        Account account = approvedFraudCheckAccount();

        account.approveAccount();

        assertTrue(account.isFraudCheckApproved());
        assertEquals(BigDecimal.ZERO, account.getLimit());
    }

    private Account notVerifiedAccount() {
        return Account.createAccount(ACCOUNT_NAME);
    }

    private Account approvedFraudCheckAccount() {
        Account account = notVerifiedAccount();
        account.startFraudCheck();
        account.approveFraudCheck();
        return account;
    }

    private Account approvedSoftCheckAccount() {
        Account account = approvedFraudCheckAccount();
        account.startSoftCheck();
        account.approveSoftCheck();
        return account;
    }

}
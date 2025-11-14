package com.chau.bankserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.chau.bankserver.entity.Account;
import com.chau.bankserver.service.AccountService;

public class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setup() {
        accountService = new AccountService();
    }

    @Test
    void testCreateAccount() {
        Account account = accountService.addAccount("Celine");
        assertEquals("Celine", account.getName());
        assertEquals(1, account.getId()); // first id
    }

    @Test
    void testDeposit() {
        Account account = accountService.addAccount("Chau");
        accountService.deposit(account.getId(), 2222);
        assertEquals(2222, account.getBalance());
    }

    @Test
    void testWithdraw() {
        Account account = accountService.addAccount("Revel");

        // With insufficient funds
        assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(account.getId(), 100);
        });

        // Add deposite first
        accountService.deposit(account.getId(), 1000);
        accountService.withdraw(account.getId(), 100);
        assertEquals(900, account.getBalance());
    }

    @Test
    void testTransfer() {
        Account from = accountService.addAccount("From");
        assertThrows(IllegalArgumentException.class, () -> {
            accountService.transfer(from.getId(), from.getId(), 100);
        });

        Account to = accountService.addAccount("To");

        // Make a correct transfer
        accountService.deposit(from.getId(), 1000);
        accountService.transfer(from.getId(), to.getId(), 500);

        // Check balance
        assertEquals(500, from.getBalance());
        assertEquals(500, to.getBalance());
    }
}

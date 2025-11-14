package com.chau.bankserver.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.chau.bankserver.entity.Account;
import com.chau.bankserver.entity.TransferArchive;

@Service
public class AccountService {
    // Incremental id
    private final AtomicLong idIncr = new AtomicLong(1);
    // Concurrent for thread safety
    private Map<Long, Account> accounts = new ConcurrentHashMap<>();

    // List all accounts
    public Collection<Account> listAll() {
        return accounts.values();
    }

    // create account
    public Account addAccount(String name) {
        long id = idIncr.getAndIncrement();
        Account newAccount = new Account(id, name);
        accounts.put(id, newAccount);
        return newAccount;
    }

    // Get Account
    private Account getAccount(long id) {
        Account account = accounts.get(id);
        if (account == null) {
            throw new IllegalArgumentException("Account not found for id : " + id);
        }
        return account;
    }

    // Make a deposit
    public Account deposit(long accountid, double amount) {
        Account account = getAccount(accountid);
        account.deposit(amount);
        return account;
    }

    // Make a withdraw
    public Account withdraw(long accountid, double amount) {
        Account account = getAccount(accountid);
        account.withdraw(amount);
        return account;
    }

    public void transfer(long fromid, long toid, double amount) {
        if (fromid == toid) {
            throw new IllegalArgumentException("Cannot transfert to the same account");
        }

        Account from = getAccount(fromid);
        Account to = getAccount(toid);

        // Avoid deadlock
        Account first = from.getId() < to.getId() ? from : to;
        Account second = from.getId() < to.getId() ? to : from;

        // begin tranfer
        synchronized(first) {
            try {
                Thread.sleep(100); // lock other operation
            } catch (Exception e) {
               Thread.currentThread().interrupt();
            }

            synchronized(second) {
                from.withdraw(amount);
                to.deposit(amount);
                // Add new outgoing transfert
                from.addTransfert(new TransferArchive(toid, amount));
            }
        }

    }
}

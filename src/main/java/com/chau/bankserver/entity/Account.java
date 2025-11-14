package com.chau.bankserver.entity;

import java.util.ArrayDeque;
import java.util.Deque;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    // Maximum Account outgoing transferts
    private static final Short MAX_TRANSFERS = 50;

    private final long id;
    private final String name;
    private double balance;
    // Use queue to remove first if full, max is 50 transferts
    private final Deque<TransferArchive> last50Transfers = new ArrayDeque<>();

    public Account(long id, String name) {
        this.id = id;
        this.name = name; 
    }

    // Deposit
    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to be deposited should be positive");
        }
        balance += amount;
    }

    // Withdraw
    public synchronized void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to be withdrawn should be positive");
        } else if (balance < amount) {
            throw new IllegalArgumentException("Withdraw impossible, insufficient money");
        }
        balance -= amount;
    }

    // Transfer money
    public synchronized void addTransfert(TransferArchive newTransfer) {
        // Remove first transaction if transfers > 50
        if (last50Transfers.size() > MAX_TRANSFERS) {
            last50Transfers.removeFirst();
        }
        // Add new transfer at the end of the queue
       last50Transfers.addLast(newTransfer);
    }

}

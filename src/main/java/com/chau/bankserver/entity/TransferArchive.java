package com.chau.bankserver.entity;

import java.time.Instant;

import lombok.Getter;

@Getter
public class TransferArchive {
    private final long toId;
    private final double amount;
    private final Instant timestamp;

    public TransferArchive(long id, double amount) {
        this.toId = id;
        this.amount = amount;
        this.timestamp = Instant.now();
    }
}

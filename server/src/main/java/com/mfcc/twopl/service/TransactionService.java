package com.mfcc.twopl.service;

import com.mfcc.twopl.model.Transaction;
import com.mfcc.twopl.persistence.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    private static AtomicLong nextId = new AtomicLong();

    public synchronized void startNewTransaction(Transaction transaction) {
        transaction.setId(nextId.getAndIncrement());
        transaction.setStatus(Transaction.Status.ACTIVE);
        transaction.setTimestamp(Instant.now());
        transactionRepo.save(transaction);
    }

    public synchronized void commitTransaction(Transaction transaction) {
        transaction.setStatus(Transaction.Status.COMMIT);
        transactionRepo.save(transaction);
    }
}

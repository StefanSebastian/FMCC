package com.mfcc.twopl.persistence.inmemory;

import com.mfcc.twopl.model.Transaction;
import com.mfcc.twopl.persistence.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class TransactionRepositoryInMemory implements TransactionRepository {

    private Map<Long, Transaction> transactions = new HashMap<>();

    @Override
    public synchronized void save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public synchronized Transaction get(long transactionId) {
        return transactions.get(transactionId);
    }

}

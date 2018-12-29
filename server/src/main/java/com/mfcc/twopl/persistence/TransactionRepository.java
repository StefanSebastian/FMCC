package com.mfcc.twopl.persistence;

import com.mfcc.twopl.model.Transaction;

/**
 * @author stefansebii@gmail.com
 */
public interface TransactionRepository {
    void save(Transaction transaction);
}

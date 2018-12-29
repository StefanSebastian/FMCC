package com.mfcc.twopl.persistence;

import com.mfcc.twopl.model.Lock;

import java.util.Collection;

/**
 * @author stefansebii@gmail.com
 */
public interface LockRepository {
    void save(Lock lock);
    Collection<Lock> getAll();
    void removeForTransaction(long transactionId);
}

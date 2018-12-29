package com.mfcc.twopl.persistence.inmemory;

import com.mfcc.twopl.model.Lock;
import com.mfcc.twopl.persistence.LockRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class LockRepositoryInMemory implements LockRepository {

    private Map<Long, Lock> locks = new HashMap<>();

    @Override
    public void save(Lock lock) {
        locks.put(lock.getId(), lock);
    }

    @Override
    public Collection<Lock> getAll() {
        return locks.values();
    }

    @Override
    public void removeForTransaction(long transactionId) {
        locks.entrySet().removeIf(entry ->
                entry.getValue().getTransactionId() == transactionId);
    }
}

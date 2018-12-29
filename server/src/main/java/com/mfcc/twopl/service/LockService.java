package com.mfcc.twopl.service;

import com.mfcc.twopl.model.Lock;
import com.mfcc.twopl.model.Operation;
import com.mfcc.twopl.model.ResourceIdentifier;
import com.mfcc.twopl.model.Transaction;
import com.mfcc.twopl.persistence.LockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class LockService {

    private Logger logger = LoggerFactory.getLogger(LockService.class);

    @Autowired
    private LockRepository lockRepo;

    private static AtomicLong nextId = new AtomicLong();

    /**
     * Create a new lock entity
     */
    public Lock createLock(Operation operation, Transaction transaction) {
        Lock lock = new Lock();
        lock.setId(nextId.getAndIncrement());
        lock.setType(operation.getType() == Operation.Type.SELECT ?
                Lock.Type.READ : Lock.Type.WRITE);
        lock.setResourceIdentifier(operation.getResourceIdentifier());
        lock.setTransactionId(transaction.getId());
        return lock;
    }

    /**
     * Attempt to acquire lock
     */
    public synchronized boolean acquireLock(Lock lock) {
        Collection<Lock> locks = lockRepo.getAll();
        for (Lock existingLock : locks) {
            if (locksConflict(existingLock, lock)){
                logConflictLock(existingLock, lock);
                return false;
            }
        }
        lockRepo.save(lock);
        return true;
    }

    private void logConflictLock(Lock existingLock, Lock lock) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Couldn't acquire lock for transaction " + lock.getTransactionId());
        logMessage.append("\n");
        logMessage.append("Conflict with already existing lock for transaction ");
        logMessage.append(existingLock.getTransactionId());
        logMessage.append("\n");
        logMessage.append("Resource for existing lock : ");
        logMessage.append(existingLock.getResourceIdentifier().toString());
        logMessage.append("\n");
        logMessage.append("Resource for new lock : ");
        logMessage.append(lock.getResourceIdentifier().toString());

        logger.debug(logMessage.toString());
    }

    public synchronized void unlockForTransaction(long transactionId) {
        lockRepo.removeForTransaction(transactionId);
    }

    /**
     * Checks if the two locks are in conflict
     */
    private boolean locksConflict(Lock firstLock, Lock secondLock) {
        // 2 shared locks
        if (firstLock.getType().equals(Lock.Type.READ) &&
                secondLock.getType().equals(Lock.Type.READ)) {
            return false;
        }

        ResourceIdentifier resId1 = firstLock.getResourceIdentifier();
        ResourceIdentifier resId2 = secondLock.getResourceIdentifier();
        // different tables
        if (!resId1.getRecord().equals(resId2.getRecord())) {
            return false;
        }

        // different records
        return recordIdConflict(resId1.getRecordId(), resId2.getRecordId());
    }

    private boolean recordIdConflict(long id1, long id2) {
        if (id1 == ResourceIdentifier.WHOLE_TABLE || id2 == ResourceIdentifier.WHOLE_TABLE) {
            return true;
        }
        return id1 == id2 && id1 != ResourceIdentifier.NONE;
    }


}

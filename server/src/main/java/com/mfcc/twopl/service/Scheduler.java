package com.mfcc.twopl.service;

import com.mfcc.twopl.exceptions.TwoPLException;
import com.mfcc.twopl.model.Lock;
import com.mfcc.twopl.model.Operation;
import com.mfcc.twopl.model.ResourceIdentifier;
import com.mfcc.twopl.model.Transaction;
import com.mfcc.twopl.persistence.LockRepository;
import com.mfcc.twopl.persistence.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class Scheduler {

    private Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private LockRepository lockRepo;

    @Autowired
    private WaitsForGraph waitsForGraph;

    private static AtomicLong nextLockId = new AtomicLong();
    private static AtomicLong nextTranId = new AtomicLong();

    public void run(Transaction transaction) {
        try {
            runTransaction(transaction);
        } catch (TwoPLException ex) {
            logger.debug(ex.getMessage());
            run(new Transaction(transaction.getOperations())); // retry transaction
        }
    }

    private void runTransaction(Transaction transaction) throws TwoPLException {
        startNewTransaction(transaction);
        logger.debug("Started transaction " + transaction.getId());

        for (Operation operation : transaction.getOperations()) {
            lockResources(operation, transaction);
            operation.getAction().run();
            operation.setExecuted(true);
        }
        removeLocks(transaction.getId());
        logger.debug("Unlocked locks for transaction " + transaction.getId());

        transaction.setStatus(Transaction.Status.COMMIT);
        transactionRepo.save(transaction);
        logger.debug("Committed transaction " + transaction.getId());
    }

    /**
     * Stores the transaction
     */
    private void startNewTransaction(Transaction transaction) {
        transaction.setId(nextTranId.getAndIncrement());
        transaction.setStatus(Transaction.Status.ACTIVE);
        transaction.setTimestamp(Instant.now());
        transactionRepo.save(transaction);
    }

    /**
     * Attempt to lock resources for operation
     */
    private void lockResources(Operation operation, Transaction transaction) throws TwoPLException {
        Lock lock = createLock(operation, transaction);
        while (!acquireLock(lock)){
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}
        }
    }

    /**
     * Create a new lock entity
     */
    private Lock createLock(Operation operation, Transaction transaction) {
        Lock lock = new Lock();
        lock.setId(nextLockId.getAndIncrement());
        lock.setType(operation.getType() == Operation.Type.SELECT ?
                Lock.Type.READ : Lock.Type.WRITE);
        lock.setResourceIdentifier(operation.getResourceIdentifier());
        lock.setTransactionId(transaction.getId());
        return lock;
    }

    /**
     * Attempt to acquire lock
     * Synchronized alongside removeLocks method, for the case in which we iterate locks
     * and another transaction is removing some locks. We would be seeing incorrect conflicts (with locks
     * that were removed).
     * Also keeps other transactions, which may be cancelled, on wait.
     */
    private synchronized boolean acquireLock(Lock lock) throws TwoPLException {
        Transaction transaction = transactionRepo.get(lock.getTransactionId());
        if (transaction.getStatus().equals(Transaction.Status.ABORT)) {
            // transaction was aborted from another thread
            throw new TwoPLException("Transaction " + transaction.getId() + " was aborted from another thread");
        }

        Collection<Lock> locks = lockRepo.getAll();
        for (Lock existingLock : locks) {
            if (locksConflict(existingLock, lock)){
                waitsForGraph.addEdge(lock.getTransactionId(), existingLock.getTransactionId());
                logConflictLock(existingLock, lock);
                handlePossibleDeadlock();
                return false;
            }
        }
        lockRepo.save(lock);
        return true;
    }

    /**
     * remove all locks for transaction
     */
    private synchronized void removeLocks(long transactionId) {
        lockRepo.removeForTransaction(transactionId);
    }

    /**
     * If there was a lock conflict we apply the continuous detection method
     * of checking the WFG
     */
    private void handlePossibleDeadlock() {
        waitsForGraph.checkForConflicts();
        if (waitsForGraph.getFoundConflict()) {
            logger.debug("Potential victims : " + waitsForGraph.getPossibleVictims());
            long victim = waitsForGraph.getPossibleVictims().stream()
                    .max(Comparator.comparing(tranId -> transactionRepo.get(tranId).getTimestamp()))
                    .get();
            logger.debug("Found a deadlock in wfg. Transaction " + victim + " chosen as victim. Strategy: kill the the youngest");
            waitsForGraph.removeEdges(victim);

            // abort transaction, remove all locks, rollback operations
            Transaction transaction = transactionRepo.get(victim);
            transaction.setStatus(Transaction.Status.ABORT);
            transactionRepo.save(transaction);
            lockRepo.removeForTransaction(transaction.getId());
            transaction.getOperations().stream()
                    .filter(Operation::isExecuted)
                    .forEach(operation -> operation.getRollback().run());
        }
    }

    /**
     * Detailed log of conflict lock
     */
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

    /**
     * Check if 2 record ids are in conflict
     */
    private boolean recordIdConflict(long id1, long id2) {
        if (id1 == ResourceIdentifier.WHOLE_TABLE || id2 == ResourceIdentifier.WHOLE_TABLE) {
            return true;
        }
        return id1 == id2 && id1 != ResourceIdentifier.NONE;
    }

}

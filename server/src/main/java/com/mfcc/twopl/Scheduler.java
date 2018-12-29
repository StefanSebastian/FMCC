package com.mfcc.twopl;

import com.mfcc.twopl.model.Lock;
import com.mfcc.twopl.model.Operation;
import com.mfcc.twopl.model.Transaction;
import com.mfcc.twopl.service.LockService;
import com.mfcc.twopl.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class Scheduler {

    Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private LockService lockService;

    @Autowired
    private TransactionService transactionService;

    public void run(Transaction transaction) {
        transactionService.startNewTransaction(transaction);
        logger.debug("Started transaction " + transaction.getId());

        for (Operation operation : transaction.getOperations()) {
            lockResources(operation, transaction);
            operation.getAction().run();
        }
        lockService.unlockForTransaction(transaction.getId());
        logger.debug("Unlocked locks for transaction " + transaction.getId());

        transactionService.commitTransaction(transaction);
        logger.debug("Committed transaction " + transaction.getId());
    }

    private void lockResources(Operation operation, Transaction transaction) {
        Lock lock = lockService.createLock(operation, transaction);
        while (!lockService.acquireLock(lock)){
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {}
        }
    }

}

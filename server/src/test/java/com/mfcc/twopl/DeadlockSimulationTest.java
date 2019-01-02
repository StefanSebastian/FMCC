package com.mfcc.twopl;

import com.mfcc.twopl.model.Operation;
import com.mfcc.twopl.model.ResourceIdentifier;
import com.mfcc.twopl.model.Transaction;
import com.mfcc.twopl.persistence.inmemory.LockRepositoryInMemory;
import com.mfcc.twopl.persistence.inmemory.TransactionRepositoryInMemory;
import com.mfcc.twopl.service.Scheduler;
import com.mfcc.twopl.service.WaitsForGraph;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;


/**
 * @author stefansebii@gmail.com
 */
public class DeadlockSimulationTest {

    private Logger logger = LoggerFactory.getLogger(DeadlockSimulationTest.class);

    public class Dummy {}

    private Scheduler scheduler;

    @Before
    public void init() {
        scheduler = new Scheduler();
        scheduler.setLockRepo(new LockRepositoryInMemory());
        scheduler.setTransactionRepo(new TransactionRepositoryInMemory());
        scheduler.setWaitsForGraph(new WaitsForGraph());
    }

    @Test
    public void deadlock() throws Exception {
        Thread t1 = new Thread(() -> {
            Transaction res = scheduler.run(getFirstTransaction());
            assertTrue(res.getStatus().equals(Transaction.Status.COMMIT));
        });
        Thread t2 = new Thread(() -> {
            Transaction res = scheduler.run(getSecondTransaction());
            assertTrue(res.getStatus().equals(Transaction.Status.COMMIT));
        });
        t1.start(); t2.start();
        t1.join(); t2.join();
    }

    private Transaction getFirstTransaction() {
        Transaction transaction = new Transaction();
        Operation operation = new Operation();
        operation.setType(Operation.Type.UPDATE);
        operation.setResourceIdentifier(new ResourceIdentifier(ConflictLocksTest.Dummy.class, 1));
        operation.setAction(() -> {
            logger.debug("Executing first operation; first transaction");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        });
        operation.setRollback(() -> logger.debug("Rollback first operation; first transaction"));

        Operation operation2 = new Operation();
        operation2.setType(Operation.Type.UPDATE);
        operation2.setResourceIdentifier(new ResourceIdentifier(ConflictLocksTest.Dummy.class, 2));
        operation2.setAction(() -> logger.debug("Executing second operation; first transaction"));
        operation2.setRollback(() -> logger.debug("Rollback second operation; first transaction"));

        transaction.setOperations(Arrays.asList(operation, operation2));
        return transaction;
    }

    private Transaction getSecondTransaction() {
        Transaction transaction = new Transaction();
        Operation operation = new Operation();
        operation.setType(Operation.Type.UPDATE);
        operation.setResourceIdentifier(new ResourceIdentifier(ConflictLocksTest.Dummy.class, 2));
        operation.setAction(() -> {
            logger.debug("Executing first operation; second transaction");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        });
        operation.setRollback(() -> logger.debug("Rollback first operation; second transaction"));

        Operation operation2 = new Operation();
        operation2.setType(Operation.Type.UPDATE);
        operation2.setResourceIdentifier(new ResourceIdentifier(ConflictLocksTest.Dummy.class, 1));
        operation2.setAction(() -> logger.debug("Executing second operation; second transaction"));
        operation2.setRollback(() -> logger.debug("Rollback second operation; second transaction"));

        transaction.setOperations(Arrays.asList(operation, operation2));
        return transaction;
    }
}

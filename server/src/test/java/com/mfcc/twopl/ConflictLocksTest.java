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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * @author stefansebii@gmail.com
 */
public class ConflictLocksTest {

    private Logger logger = LoggerFactory.getLogger(ConflictLocksTest.class);

    public class Dummy {}

    private Scheduler scheduler;

    @Before
    public void init() {
        scheduler = new Scheduler();
        scheduler.setLockRepo(new LockRepositoryInMemory());
        scheduler.setTransactionRepo(new TransactionRepositoryInMemory());
        scheduler.setWaitsForGraph(new WaitsForGraph());
    }

    private List<Operation> executed = new LinkedList<>();

    @Test
    public void twoInserts() throws Exception {
        Thread t1 = new Thread(() -> scheduler.run(getDummyUpdateTransaction(1000)));
        Thread t2 = new Thread(() -> scheduler.run(getDummyUpdateTransaction(500)));
        t1.start(); t2.start();
        t1.join(); t2.join();
        assertTrue(executed.size() == 2);
    }

    private Transaction getDummyUpdateTransaction(long sleep) {
        Transaction transaction = new Transaction();
        Operation operation = new Operation();
        operation.setType(Operation.Type.UPDATE);
        operation.setResourceIdentifier(new ResourceIdentifier(Dummy.class, 1));
        operation.setAction(() -> {
            executed.add(operation);
            logger.debug("Executing dummy update; with sleep of " + sleep + " for transaction " + transaction.getId());
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignored) {}
        });
        transaction.setOperations(Collections.singletonList(operation));
        return transaction;
    }

}

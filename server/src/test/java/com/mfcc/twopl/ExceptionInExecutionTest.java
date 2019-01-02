package com.mfcc.twopl;

import com.mfcc.twopl.exceptions.TwoPLException;
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
import java.util.Collections;

/**
 * @author stefansebii@gmail.com
 */
public class ExceptionInExecutionTest {
    private Logger logger = LoggerFactory.getLogger(ExceptionInExecutionTest.class);

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
    public void oneCrashing() {
        scheduler.run(getSingleCrashingOperation());
    }

    @Test
    public void oneOkOneCrashing() {
        scheduler.run(getOneOkOneCrashingOperation());
    }

    private Transaction getSingleCrashingOperation() {
        Transaction transaction = new Transaction();
        Operation operation = new Operation();
        operation.setType(Operation.Type.INSERT);
        operation.setResourceIdentifier(new ResourceIdentifier(SimpleInsertTest.Dummy.class, ResourceIdentifier.NONE));
        operation.setAction(() -> {
            logger.debug("Executing dummy insert");
            throw new TwoPLException("Error");
        });
        transaction.setOperations(Collections.singletonList(operation));
        return transaction;
    }

    private Transaction getOneOkOneCrashingOperation() {
        Transaction transaction = new Transaction();
        Operation operation = new Operation();
        operation.setType(Operation.Type.INSERT);
        operation.setResourceIdentifier(new ResourceIdentifier(SimpleInsertTest.Dummy.class, ResourceIdentifier.NONE));
        operation.setAction(() -> logger.debug("Executing ok operation"));
        operation.setRollback(() -> logger.debug("Executing rollback"));

        Operation operation2 = new Operation();
        operation2.setType(Operation.Type.INSERT);
        operation2.setResourceIdentifier(new ResourceIdentifier(SimpleInsertTest.Dummy.class, ResourceIdentifier.NONE));
        operation2.setAction(() -> {
            logger.debug("Executing crashing operation");
            throw new TwoPLException("Error");
        });

        transaction.setOperations(Arrays.asList(operation, operation2));
        return transaction;
    }
}

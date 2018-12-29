package com.mfcc.twopl;

import com.mfcc.Application;
import com.mfcc.twopl.exceptions.TwoPLException;
import com.mfcc.twopl.model.Operation;
import com.mfcc.twopl.model.ResourceIdentifier;
import com.mfcc.twopl.model.Transaction;
import com.mfcc.twopl.service.Scheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author stefansebii@gmail.com
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ExceptionInExecutionTest {
    private Logger logger = LoggerFactory.getLogger(ExceptionInExecutionTest.class);

    public class Dummy {}

    @Autowired
    private Scheduler scheduler;

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

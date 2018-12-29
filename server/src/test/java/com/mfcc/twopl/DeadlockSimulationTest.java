package com.mfcc.twopl;

import com.mfcc.Application;
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

/**
 * @author stefansebii@gmail.com
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class DeadlockSimulationTest {

    private Logger logger = LoggerFactory.getLogger(DeadlockSimulationTest.class);

    public class Dummy {}

    @Autowired
    private Scheduler scheduler;

    @Test
    public void deadlock() throws Exception {
        Thread t1 = new Thread(() -> scheduler.run(getFirstTransaction()));
        Thread t2 = new Thread(() -> scheduler.run(getSecondTransaction()));
        t1.start(); t2.start();
        t1.join(); t2.join();
    }

    private Transaction getFirstTransaction() {
        Transaction transaction = new Transaction();
        Operation operation = new Operation();
        operation.setType(Operation.Type.UPDATE);
        operation.setResourceIdentifier(new ResourceIdentifier(ConflictLocksTest.Dummy.class, 1));
        operation.setAction(() -> {
            logger.debug("Executing first operation");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        });

        Operation operation2 = new Operation();
        operation2.setType(Operation.Type.UPDATE);
        operation2.setResourceIdentifier(new ResourceIdentifier(ConflictLocksTest.Dummy.class, 2));
        operation2.setAction(() -> logger.debug("Executing second operation"));

        transaction.setOperations(Arrays.asList(operation, operation2));
        return transaction;
    }

    private Transaction getSecondTransaction() {
        Transaction transaction = new Transaction();
        Operation operation = new Operation();
        operation.setType(Operation.Type.UPDATE);
        operation.setResourceIdentifier(new ResourceIdentifier(ConflictLocksTest.Dummy.class, 2));
        operation.setAction(() -> {
            logger.debug("Executing first operation");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        });

        Operation operation2 = new Operation();
        operation2.setType(Operation.Type.UPDATE);
        operation2.setResourceIdentifier(new ResourceIdentifier(ConflictLocksTest.Dummy.class, 1));
        operation2.setAction(() -> logger.debug("Executing second operation"));

        transaction.setOperations(Arrays.asList(operation, operation2));
        return transaction;
    }
}

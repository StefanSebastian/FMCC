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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static junit.framework.TestCase.assertTrue;

/**
 * @author stefansebii@gmail.com
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class SimpleInsertTest {

    private Logger logger = LoggerFactory.getLogger(SimpleInsertTest.class);

    public class Dummy {}

    @Autowired
    private Scheduler scheduler;

    private final long MAX_SLEEP_TIME = 200;
    private List<Operation> executed = new LinkedList<>();

    @Test
    public void singleThreadSchedule() {
        Transaction res = scheduler.run(getDummyInsertTransaction());
        assertTrue(executed.size() == 1);
        assertTrue(res.getStatus().equals(Transaction.Status.COMMIT));
    }

    @Test
    public void runFromTwoThreads() throws Exception {
        Thread t1 = new Thread(() -> scheduler.run(getDummyInsertTransaction()));
        Thread t2 = new Thread(() -> scheduler.run(getDummyInsertTransaction()));
        t1.start(); t2.start();
        t1.join(); t2.join();
        assertTrue(executed.size() == 2);
    }

    @Test
    public void runFromManyThreads() throws Exception {
        List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < 300; i++) {
            threads.add(new Thread(() -> scheduler.run(getDummyInsertTransaction())));
        }
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
        });
        assertTrue(executed.size() == 300);
    }

    private Transaction getDummyInsertTransaction() {
        Transaction transaction = new Transaction();
        Operation operation = new Operation();
        operation.setType(Operation.Type.INSERT);
        operation.setResourceIdentifier(new ResourceIdentifier(Dummy.class, ResourceIdentifier.NONE));
        operation.setAction(() -> {
            executed.add(operation);
            logger.debug("Executing dummy insert");
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(MAX_SLEEP_TIME));
            } catch (InterruptedException ignored) {}
        });
        transaction.setOperations(Collections.singletonList(operation));
        return transaction;
    }

}

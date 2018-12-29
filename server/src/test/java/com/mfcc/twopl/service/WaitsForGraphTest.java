package com.mfcc.twopl.service;

import com.mfcc.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * @author stefansebii@gmail.com
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class WaitsForGraphTest {

    private Logger logger = LoggerFactory.getLogger(WaitsForGraphTest.class);

    @Test
    public void noDeadlocks() {
        WaitsForGraph wfg = new WaitsForGraph();
        wfg.addEdge(1, 2);
        wfg.addEdge(2, 3);
        wfg.addEdge(1, 4);

        wfg.checkForConflicts();
        assertTrue(!wfg.getFoundConflict());
    }

    @Test
    public void withDeadlock() {
        WaitsForGraph wfg = new WaitsForGraph();
        wfg.addEdge(1, 2);
        wfg.addEdge(2, 3);
        wfg.addEdge(3, 4);
        wfg.addEdge(4, 3);
        wfg.addEdge(3, 2);

        wfg.checkForConflicts();
        assertTrue(wfg.getFoundConflict());
        logger.debug(wfg.getPossibleVictims().toString());
    }

    @Test
    public void noCyclesTricky() {
        WaitsForGraph wfg = new WaitsForGraph();
        wfg.addEdge(0, 3);
        wfg.addEdge(0, 1);
        wfg.addEdge(3, 4);
        wfg.addEdge(1, 2);
        wfg.addEdge(4, 2);

        wfg.checkForConflicts();
        assertTrue(!wfg.getFoundConflict());
    }

    @Test
    public void withDeadlockOf3() {
        WaitsForGraph wfg = new WaitsForGraph();
        wfg.addEdge(1, 2);
        wfg.addEdge(2, 3);
        wfg.addEdge(3, 1);

        wfg.checkForConflicts();
        assertTrue(wfg.getFoundConflict());
        logger.debug(wfg.getPossibleVictims().toString());
        assertTrue(wfg.getPossibleVictims().size() == 3);
    }

    @Test
    public void withDeadlockOf1() {
        WaitsForGraph wfg = new WaitsForGraph();
        wfg.addEdge(1, 2);
        wfg.addEdge(2, 3);
        wfg.addEdge(3, 2);

        wfg.checkForConflicts();
        assertTrue(wfg.getFoundConflict());
        logger.debug(wfg.getPossibleVictims().toString());
        assertTrue(wfg.getPossibleVictims().size() == 2);
    }
}

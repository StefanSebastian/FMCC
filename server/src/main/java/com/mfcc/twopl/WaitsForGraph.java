package com.mfcc.twopl;

/**
 * TODO does not feel like the right design
 * @author stefansebii@gmail.com
 */
public class WaitsForGraph {

    private Lock lock;
    private Transaction tranHasLock;
    private Transaction tranWaitsLock;

    public WaitsForGraph() {}

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Transaction getTranHasLock() {
        return tranHasLock;
    }

    public void setTranHasLock(Transaction tranHasLock) {
        this.tranHasLock = tranHasLock;
    }

    public Transaction getTranWaitsLock() {
        return tranWaitsLock;
    }

    public void setTranWaitsLock(Transaction tranWaitsLock) {
        this.tranWaitsLock = tranWaitsLock;
    }
}

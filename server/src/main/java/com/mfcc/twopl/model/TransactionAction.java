package com.mfcc.twopl.model;

import com.mfcc.twopl.exceptions.TwoPLException;

/**
 * @author stefansebii@gmail.com
 *
 * Interface that marks an action implemented by the transaction.
 * In case of failure a TwoPlException is thrown.
 */
public interface TransactionAction {
    void run() throws TwoPLException;
}

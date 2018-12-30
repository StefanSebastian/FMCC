package com.mfcc.beer_shop.service;

import com.mfcc.beer_shop.model.Beer;
import com.mfcc.beer_shop.model.Stock;
import com.mfcc.twopl.model.Operation;
import com.mfcc.twopl.model.ResourceIdentifier;
import com.mfcc.twopl.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author stefansebii@gmail.com
 */
@Service
public class BeerService {

    private Logger logger = LoggerFactory.getLogger(BeerService.class);

    public void storeBeer(Beer beer, Stock stock){
        Transaction transaction = new Transaction();
        Operation storeBeerOp = new Operation();
        storeBeerOp.setType(Operation.Type.INSERT);
        storeBeerOp.setResourceIdentifier(new ResourceIdentifier(Beer.class, ResourceIdentifier.NONE));
        storeBeerOp.setAction(() -> logger.debug("Storing beer"));
        storeBeerOp.setRollback(() -> logger.debug("Rollback beer store"));

        Operation storeStockOp = new Operation();
        storeStockOp.setType(Operation.Type.INSERT);
        storeStockOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, ResourceIdentifier.NONE));
        storeStockOp.setAction(() -> logger.debug("Storing stock"));
        storeStockOp.setRollback(() -> logger.debug("Rollback store stock"));
    }
}

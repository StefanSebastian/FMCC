package com.mfcc.beer_shop.service;

import com.mfcc.beer_shop.dto.BeerDto;
import com.mfcc.beer_shop.dto.OrderDto;
import com.mfcc.beer_shop.dto.OrderItemDto;
import com.mfcc.beer_shop.exception.ServiceException;
import com.mfcc.beer_shop.model.Beer;
import com.mfcc.beer_shop.model.Receipt;
import com.mfcc.beer_shop.model.Stock;
import com.mfcc.beer_shop.persistence.BeerPersistence;
import com.mfcc.twopl.model.Operation;
import com.mfcc.twopl.model.ResourceIdentifier;
import com.mfcc.twopl.model.Transaction;
import com.mfcc.twopl.service.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author stefansebii@gmail.com
 */
@Service
public class BeerService {

    private Logger logger = LoggerFactory.getLogger(BeerService.class);

    @Autowired
    private BeerPersistence persistence;

    @Autowired
    private Scheduler tranScheduler;

    private void runTransaction(Transaction transaction) throws ServiceException {
        transaction = tranScheduler.run(transaction);
        logger.debug("Tran executed; Status : " + transaction.getStatus()
                + "; Error message " + transaction.getErrorMessage());
        if (transaction.getStatus().equals(Transaction.Status.ABORT)) {
            throw new ServiceException(transaction.getErrorMessage());
        }
    }

    public void storeBeer(Beer beer, Stock stock) throws ServiceException {
        Transaction transaction = new Transaction();
        Operation storeBeerOp = new Operation();
        storeBeerOp.setType(Operation.Type.INSERT);
        storeBeerOp.setResourceIdentifier(new ResourceIdentifier(Beer.class, ResourceIdentifier.NONE));

        Operation storeStockOp = new Operation();
        storeStockOp.setType(Operation.Type.INSERT);
        storeStockOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, ResourceIdentifier.NONE));

        persistence.storeBeerQueries(storeBeerOp, storeStockOp, beer, stock);

        transaction.setOperations(Arrays.asList(storeBeerOp, storeStockOp));
        runTransaction(transaction);
    }

    public Receipt makeOrder(OrderDto orderDto) throws ServiceException {
        List<OrderItemDto> orderItems = orderDto.getOrderItems();
        Transaction transaction = new Transaction();
        List<Operation> operations = new LinkedList<>();

        for (OrderItemDto order : orderItems) {
            Operation updateStockOp = new Operation();
            updateStockOp.setType(Operation.Type.UPDATE);
            updateStockOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, order.getBeerId()));
            persistence.updateStockQuery(updateStockOp, order.getBeerId(), order.getAmount(), orderDto.getDemoSleep());
            operations.add(updateStockOp);
        }

        Operation addReceiptOp = new Operation();
        addReceiptOp.setType(Operation.Type.INSERT);
        addReceiptOp.setResourceIdentifier(new ResourceIdentifier(Receipt.class, ResourceIdentifier.NONE));
        Receipt receipt = generateReceipt(orderDto);
        persistence.addReceiptQuery(receipt, addReceiptOp);
        operations.add(addReceiptOp);

        transaction.setOperations(operations);
        runTransaction(transaction);
        return receipt;
    }

    private Receipt generateReceipt(OrderDto orderDto) {
        Receipt receipt = new Receipt();
        receipt.setAddress(orderDto.getAddress());
        String description = "";
        float totalPrice = 0;
        for (OrderItemDto orderItem : orderDto.getOrderItems()) {
            totalPrice += orderItem.getPrice();
            description = description + orderItem.getBeerName() + ":" + orderItem.getAmount() + "\n";
        }
        receipt.setDescription(description);
        receipt.setTotalPrice(totalPrice);
        return receipt;
    }

    public List<BeerDto> getBeers(boolean availableFilter) throws ServiceException {
        Transaction transaction = new Transaction();
        Operation readBeerOp = new Operation();
        readBeerOp.setType(Operation.Type.SELECT);
        readBeerOp.setResourceIdentifier(new ResourceIdentifier(Beer.class, ResourceIdentifier.WHOLE_TABLE));
        List<Beer> beers = persistence.readBeersQuery(readBeerOp); // will be populated after tran

        Operation readStockOp = new Operation();
        readStockOp.setType(Operation.Type.SELECT);
        readStockOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, ResourceIdentifier.WHOLE_TABLE));
        List<Stock> stocks = persistence.readStocksQuery(readStockOp);

        transaction.setOperations(Arrays.asList(readBeerOp, readStockOp));
        runTransaction(transaction);
        return buildBeerList(beers, stocks, availableFilter);
    }

    private List<BeerDto> buildBeerList(List<Beer> beers, List<Stock> stocks, boolean availableFilter) {
        List<BeerDto> beerDtos = new LinkedList<>();

        beers.forEach(beer -> {
            Stock correspStock = stocks.stream()
                    .filter(stock -> stock.getBeerId() == beer.getId())
                    .findAny().get();
            BeerDto beerDto = new BeerDto();
            beerDto.fillBeerDetails(beer);
            beerDto.fillStockDetails(correspStock);
            if (!availableFilter || correspStock.getAvailable() > 0) {
                beerDtos.add(beerDto);
            }
        });

        return beerDtos;
    }

    public void updateStock(long beerId, int additionalStock) throws ServiceException {
        Transaction transaction = new Transaction();
        Operation updateStockOp = new Operation();
        updateStockOp.setType(Operation.Type.UPDATE);
        updateStockOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, beerId));
        persistence.updateStockQuery(updateStockOp, beerId, additionalStock);
        transaction.setOperations(Collections.singletonList(updateStockOp));
        runTransaction(transaction);
    }

    public void updatePrice(long beerId, float newPrice) throws ServiceException {
        Transaction transaction = new Transaction();

        Operation readPriceOp = new Operation();
        readPriceOp.setType(Operation.Type.SELECT);
        readPriceOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, beerId));

        Operation updatePriceOp = new Operation();
        updatePriceOp.setType(Operation.Type.UPDATE);
        updatePriceOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, beerId));

        persistence.updatePriceQueries(readPriceOp, updatePriceOp, beerId, newPrice);

        transaction.setOperations(Arrays.asList(readPriceOp, updatePriceOp));
        runTransaction(transaction);
    }

}

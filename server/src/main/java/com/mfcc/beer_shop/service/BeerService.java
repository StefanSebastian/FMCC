package com.mfcc.beer_shop.service;

import com.mfcc.beer_shop.dto.OrderDto;
import com.mfcc.beer_shop.dto.OrderItemDto;
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

    public void storeBeer(Beer beer, Stock stock){
        Transaction transaction = new Transaction();
        Operation storeBeerOp = new Operation();
        storeBeerOp.setType(Operation.Type.INSERT);
        storeBeerOp.setResourceIdentifier(new ResourceIdentifier(Beer.class, ResourceIdentifier.NONE));

        Operation storeStockOp = new Operation();
        storeStockOp.setType(Operation.Type.INSERT);
        storeStockOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, ResourceIdentifier.NONE));

        persistence.storeBeerQueries(storeBeerOp, storeStockOp, beer, stock);

        transaction.setOperations(Arrays.asList(storeBeerOp, storeStockOp));
        transaction = tranScheduler.run(transaction);
        logger.debug("Tran executed; Status : " + transaction.getStatus()
                + "; Error message " + transaction.getErrorMessage());
    }

    public void makeOrder(OrderDto orderDto) {
        List<OrderItemDto> orderItems = orderDto.getOrderItems();
        Transaction transaction = new Transaction();
        List<Operation> operations = new LinkedList<>();

        for (OrderItemDto order : orderItems) {
            Operation updateStockOp = new Operation();
            updateStockOp.setType(Operation.Type.UPDATE);
            updateStockOp.setResourceIdentifier(new ResourceIdentifier(Stock.class, order.getBeerId()));
            persistence.updateStockQuery(updateStockOp, order.getBeerId(), order.getAmount());
            operations.add(updateStockOp);
        }

        Operation addReceiptOp = new Operation();
        addReceiptOp.setType(Operation.Type.INSERT);
        addReceiptOp.setResourceIdentifier(new ResourceIdentifier(Receipt.class, ResourceIdentifier.NONE));
        persistence.addReceiptQuery(generateReceipt(orderDto), addReceiptOp);
        operations.add(addReceiptOp);

        transaction.setOperations(operations);
        transaction = tranScheduler.run(transaction);
        logger.debug("Tran executed; Status : " + transaction.getStatus()
                + "; Error message " + transaction.getErrorMessage());
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
}

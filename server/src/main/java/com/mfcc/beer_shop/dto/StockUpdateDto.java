package com.mfcc.beer_shop.dto;

/**
 * @author stefansebii@gmail.com
 */
public class StockUpdateDto {
    private long beerId;
    private int additionalStock;

    public StockUpdateDto() {}

    public long getBeerId() {
        return beerId;
    }

    public void setBeerId(long beerId) {
        this.beerId = beerId;
    }

    public int getAdditionalStock() {
        return additionalStock;
    }

    public void setAdditionalStock(int additionalStock) {
        this.additionalStock = additionalStock;
    }
}

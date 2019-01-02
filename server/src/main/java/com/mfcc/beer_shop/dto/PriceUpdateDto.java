package com.mfcc.beer_shop.dto;

/**
 * @author stefansebii@gmail.com
 */
public class PriceUpdateDto {
    private long beerId;
    private float newPrice;

    public PriceUpdateDto() {}

    public long getBeerId() {
        return beerId;
    }

    public void setBeerId(long beerId) {
        this.beerId = beerId;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }
}

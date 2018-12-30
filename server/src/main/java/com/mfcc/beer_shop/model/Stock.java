package com.mfcc.beer_shop.model;

/**
 * @author stefansebii@gmail.com
 */
public class Stock {
    private long beerId;
    private int available;
    private float price;

    public Stock() {}

    public Stock(int available, float price) {
        this.available = available;
        this.price = price;
    }

    public long getBeerId() {
        return beerId;
    }

    public void setBeerId(long beerId) {
        this.beerId = beerId;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

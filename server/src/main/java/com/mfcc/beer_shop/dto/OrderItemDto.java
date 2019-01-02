package com.mfcc.beer_shop.dto;

/**
 * @author stefansebii@gmail.com
 */
public class OrderItemDto {
    private long beerId;
    private String beerName;
    private int amount;
    private float price;

    public OrderItemDto() {}

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public long getBeerId() {
        return beerId;
    }

    public void setBeerId(long beerId) {
        this.beerId = beerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

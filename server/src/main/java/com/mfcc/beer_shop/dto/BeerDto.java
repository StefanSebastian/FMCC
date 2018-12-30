package com.mfcc.beer_shop.dto;

import com.mfcc.beer_shop.model.Beer;
import com.mfcc.beer_shop.model.Stock;

/**
 * @author stefansebii@gmail.com
 */
public class BeerDto {
    private String name;
    private String style;
    private String description;
    private String producer;
    private int available;
    private float price;

    public BeerDto() {}

    public Beer getBeer() {
        return new Beer(name, style, description, producer);
    }

    public Stock getStock() {
        return new Stock(available, price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
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

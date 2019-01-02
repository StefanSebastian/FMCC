package com.mfcc.beer_shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mfcc.beer_shop.model.Beer;
import com.mfcc.beer_shop.model.Stock;

/**
 * @author stefansebii@gmail.com
 */
public class BeerDto {
    private long id;
    private String name;
    private String style;
    private String description;
    private String producer;
    private int available;
    private float price;

    public BeerDto() {}

    @JsonIgnore
    public Beer getBeer() {
        return new Beer(name, style, description, producer);
    }

    @JsonIgnore
    public Stock getStock() {
        return new Stock(available, price);
    }

    @JsonIgnore
    public void fillBeerDetails(Beer beer){
        this.setId(beer.getId());
        this.setDescription(beer.getDescription());
        this.setName(beer.getName());
        this.setProducer(beer.getProducer());
        this.setStyle(beer.getStyle());
    }

    @JsonIgnore
    public void fillStockDetails(Stock stock) {
        this.setAvailable(stock.getAvailable());
        this.setPrice(stock.getPrice());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

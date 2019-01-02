package com.mfcc.beer_shop.dto;

import com.mfcc.beer_shop.model.Receipt;

/**
 * @author stefansebii@gmail.com
 */
public class ReceiptDto {
    private String address;
    private String description;

    public ReceiptDto() {}

    public ReceiptDto(Receipt receipt) {
        this.address = receipt.getAddress();
        this.description = receipt.getDescription();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

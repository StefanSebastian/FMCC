package com.mfcc.beer_shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stefansebii@gmail.com
 */
@RestController
public class ClientController {

    @Value("${storage.first.db.url}")
    private String url1;

    @RequestMapping("/")
    public String index() {
        return url1;
    }

}

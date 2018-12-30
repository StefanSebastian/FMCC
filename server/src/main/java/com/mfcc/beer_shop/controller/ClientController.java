package com.mfcc.beer_shop.controller;

import com.mfcc.beer_shop.dto.BeerDto;
import com.mfcc.beer_shop.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stefansebii@gmail.com
 */
@RestController
public class ClientController {

    @Autowired
    private BeerService beerService;

    @PostMapping("/beer")
    ResponseEntity storeBeer(@RequestBody BeerDto beerDto) {
        beerService.storeBeer(beerDto.getBeer(), beerDto.getStock());
        return ResponseEntity.ok(beerDto);
    }

}

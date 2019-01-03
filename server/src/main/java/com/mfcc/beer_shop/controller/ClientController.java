package com.mfcc.beer_shop.controller;

import com.mfcc.beer_shop.dto.*;
import com.mfcc.beer_shop.exception.ServiceException;
import com.mfcc.beer_shop.model.Receipt;
import com.mfcc.beer_shop.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author stefansebii@gmail.com
 */
@RestController
@RequestMapping("/shop")
@CrossOrigin // all origins
public class ClientController {

    @Autowired
    private BeerService beerService;

    @PostMapping("/store")
    ResponseEntity storeBeer(@RequestBody BeerDto beerDto) {
        try {
            beerService.storeBeer(beerDto.getBeer(), beerDto.getStock());
            return ResponseEntity.ok("Beer add successful");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/order")
    ResponseEntity orderBeer(@RequestBody OrderDto orderDto) {
        try {
            Receipt receipt = beerService.makeOrder(orderDto);
            return ResponseEntity.ok(new ReceiptDto(receipt));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/beer")
    ResponseEntity getBeers(@RequestParam("availableFilter") boolean availableFilter) {
        try {
            List<BeerDto> beers = beerService.getBeers(availableFilter);
            return ResponseEntity.ok(beers);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/beer/stock")
    ResponseEntity updateStock(@RequestBody StockUpdateDto stockUpdateDto) {
        try {
            beerService.updateStock(stockUpdateDto.getBeerId(), stockUpdateDto.getAdditionalStock());
            return ResponseEntity.ok("Stock updated");
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/beer/price")
    ResponseEntity updatePrice(@RequestBody PriceUpdateDto priceUpdateDto) {
        try {
            beerService.updatePrice(priceUpdateDto.getBeerId(), priceUpdateDto.getNewPrice());
            return ResponseEntity.ok("Price updated");
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}

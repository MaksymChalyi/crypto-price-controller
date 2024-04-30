package com.maksimkaxxl.cryptopricecontroller.controllers;

import com.maksimkaxxl.cryptopricecontroller.dtos.CryptoPriceDTO;
import com.maksimkaxxl.cryptopricecontroller.exceptions.InvalidSymbolException;
import com.maksimkaxxl.cryptopricecontroller.services.CryptoPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CryptoPriceController {

    private final CryptoPriceService priceService;

    @GetMapping("/price/{pair}")
    public ResponseEntity<CryptoPriceDTO> getPrice(@PathVariable String pair)  {
        return ResponseEntity.ok().body(priceService.getPrice(pair.toUpperCase()));
    }
}

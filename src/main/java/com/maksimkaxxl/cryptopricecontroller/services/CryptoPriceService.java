package com.maksimkaxxl.cryptopricecontroller.services;


import com.maksimkaxxl.cryptopricecontroller.dtos.CryptoPriceDTO;
import com.maksimkaxxl.cryptopricecontroller.exceptions.InvalidSymbolException;

public interface CryptoPriceService {
    CryptoPriceDTO getPrice(String pair) throws InvalidSymbolException;
    void updatePrices() throws InvalidSymbolException;

}

package com.maksimkaxxl.cryptopricecontroller.services.impl;

import com.maksimkaxxl.cryptopricecontroller.dtos.CryptoPriceDTO;
import com.maksimkaxxl.cryptopricecontroller.entity.CryptoPrice;
import com.maksimkaxxl.cryptopricecontroller.exceptions.InvalidSymbolException;
import com.maksimkaxxl.cryptopricecontroller.repositories.CryptoPriceRepository;
import com.maksimkaxxl.cryptopricecontroller.services.CryptoPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.maksimkaxxl.cryptopricecontroller.util.Constants.Endpoint.BASE_URL;
import static com.maksimkaxxl.cryptopricecontroller.util.Constants.Endpoint.PREMIUM_INDEX;
import static com.maksimkaxxl.cryptopricecontroller.util.Constants.ErrorMessage.INVALID_SYMBOL;
import static com.maksimkaxxl.cryptopricecontroller.util.Constants.QueryParam.SYMBOL;

@Service
@EnableAsync
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CryptoPriceServiceImpl implements CryptoPriceService {

    private final RestTemplate restTemplate;

    private final CryptoPriceRepository priceRepository;

    @Override
    @Transactional
    public CryptoPriceDTO getPrice(String pair) {
        CryptoPrice cryptoPrice = priceRepository.findBySymbol(pair.toUpperCase());
        if (cryptoPrice != null) {
            return new CryptoPriceDTO(cryptoPrice.getSymbol(), cryptoPrice.getPrice());
        } else {
            double price = fetchPrice(pair);
            CryptoPrice newPrice = new CryptoPrice();
            newPrice.setSymbol(pair.toUpperCase());
            newPrice.setPrice(price);
            priceRepository.save(newPrice);
            return new CryptoPriceDTO(newPrice.getSymbol(), newPrice.getPrice());
        }
    }


    @Async
    @Override
    @Transactional
    public void updatePrices() throws InvalidSymbolException {
        List<CryptoPrice> pairs = priceRepository.findAllByOrderBySymbolAsc();
        for (CryptoPrice pair : pairs) {
            String pairSymbol = pair.getSymbol();
            double price = fetchPrice(pairSymbol);
            saveOrUpdatePrice(pairSymbol, price);
        }
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void scheduledUpdatePrices() {
        updatePrices();
    }


    private double fetchPrice(String pair) {
        try {
            String url = BASE_URL + PREMIUM_INDEX + SYMBOL + pair;
            BinancePriceResponse response = restTemplate.getForObject(url, BinancePriceResponse.class);
            CryptoPriceDTO dto = new CryptoPriceDTO(response.symbol, response.markPrice);

            return dto.price();
        } catch (Exception e) {
            throw new InvalidSymbolException(INVALID_SYMBOL + pair);
        }
    }


    private void saveOrUpdatePrice(String symbol, double price) {
        CryptoPrice cryptoPrice = priceRepository.findBySymbol(symbol);
        if (cryptoPrice != null) {
            cryptoPrice.setPrice(price);
        } else {
            cryptoPrice = new CryptoPrice();
            cryptoPrice.setSymbol(symbol);
            cryptoPrice.setPrice(price);
        }
        priceRepository.save(cryptoPrice);
    }

    // Inner class for response mapping
    private static class BinancePriceResponse {
        public String symbol;

        public double markPrice;
    }
}
package com.maksimkaxxl.cryptopricecontroller.repositories;

import com.maksimkaxxl.cryptopricecontroller.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {

    List<CryptoPrice> findAllByOrderBySymbolAsc();

    CryptoPrice findBySymbol(String symbol);
}

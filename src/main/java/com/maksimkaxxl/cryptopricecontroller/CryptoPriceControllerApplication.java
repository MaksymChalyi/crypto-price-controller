package com.maksimkaxxl.cryptopricecontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoPriceControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoPriceControllerApplication.class, args);
    }

}

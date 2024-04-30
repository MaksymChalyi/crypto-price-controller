package com.maksimkaxxl.cryptopricecontroller.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "crypto_price")
public class CryptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private double price;
}

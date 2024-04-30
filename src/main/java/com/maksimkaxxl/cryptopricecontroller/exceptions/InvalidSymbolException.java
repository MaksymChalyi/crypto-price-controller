package com.maksimkaxxl.cryptopricecontroller.exceptions;

public class InvalidSymbolException extends RuntimeException {
    public InvalidSymbolException(String message) {
        super(message);
    }
}

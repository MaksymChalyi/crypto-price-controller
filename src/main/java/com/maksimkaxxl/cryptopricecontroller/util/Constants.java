package com.maksimkaxxl.cryptopricecontroller.util;

public class Constants {

    public static final class Endpoint {
        public static final String BASE_URL = "https://fapi.binance.com/fapi/v1/";
        public static final String PREMIUM_INDEX = "premiumIndex";
    }

    public static final class QueryParam {
        public static final String SYMBOL = "?symbol=";
    }

    public static final class ErrorMessage {
        public static final String INVALID_SYMBOL = "Невалідний символ: ";
    }
}

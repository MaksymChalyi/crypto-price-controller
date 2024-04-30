CREATE TABLE crypto_price
(
    id     BIGSERIAL PRIMARY KEY,
    symbol VARCHAR(10)      NOT NULL,
    price  NUMERIC(15, 8)   NOT NULL
);

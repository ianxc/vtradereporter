CREATE SEQUENCE trade_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE trade
(
    id               BIGINT DEFAULT NEXT VALUE FOR trade_seq PRIMARY KEY,
    buyer_party      VARCHAR(255)             NOT NULL,
    seller_party     VARCHAR(255)             NOT NULL,
    premium_amount   DECIMAL                  NOT NULL,
    premium_currency VARCHAR(255)             NOT NULL,
    create_time      TIMESTAMP WITH TIME ZONE NOT NULL
);

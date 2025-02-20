CREATE TABLE trade
(
    id               BIGINT                   NOT NULL,
    buyer_party      VARCHAR(255)             NOT NULL,
    seller_party     VARCHAR(255)             NOT NULL,
    premium_amount   DECIMAL                  NOT NULL,
    premium_currency VARCHAR(255)             NOT NULL,
    create_time      TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_trade PRIMARY KEY (id)
);

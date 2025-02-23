CREATE INDEX idx_trade_buyer_party
    ON trade (buyer_party);

CREATE INDEX idx_trade_create_time_desc_id
    ON trade (create_time DESC, id);

CREATE INDEX idx_trade_premium_amount
    ON trade (premium_amount);

CREATE INDEX idx_trade_premium_currency
    ON trade (premium_currency);

CREATE INDEX idx_trade_seller_party
    ON trade (seller_party);

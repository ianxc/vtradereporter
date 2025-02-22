package com.ianxc.vtradereporter.service.criteria;

public interface TradeFilterFactory {
    TradeFilter getPredefinedFilter(TradePredefinedFilterKind kind);
}

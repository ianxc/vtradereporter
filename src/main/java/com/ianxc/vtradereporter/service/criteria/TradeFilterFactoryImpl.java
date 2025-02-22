package com.ianxc.vtradereporter.service.criteria;

import org.springframework.stereotype.Component;

@Component
public class TradeFilterFactoryImpl implements TradeFilterFactory {
    
    @Override
    public TradeFilter getPredefinedFilter(TradePredefinedFilterKind kind) {
        return switch (kind) {
            case ANY -> AnyTradeFilter.INSTANCE;
            case CHALLENGE -> ChallengeTradeFilter.INSTANCE;
        };
    }
}

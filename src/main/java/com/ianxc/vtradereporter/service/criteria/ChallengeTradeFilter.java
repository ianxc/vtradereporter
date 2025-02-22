package com.ianxc.vtradereporter.service.criteria;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import com.ianxc.vtradereporter.repo.entity.TradeEntity_;
import com.ianxc.vtradereporter.service.query.TextCalc;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component("challenge")
public class ChallengeTradeFilter implements TradeFilter {
    private static final Specification<TradeEntity> dbFilter = (root, query, builder) ->
            builder.or(
                    builder.and(
                            builder.equal(root.get(TradeEntity_.sellerParty), "EMU_BANK"),
                            builder.equal(root.get(TradeEntity_.premiumCurrency), "AUD")
                    ),
                    builder.and(
                            builder.equal(root.get(TradeEntity_.sellerParty), "BISON_BANK"),
                            builder.equal(root.get(TradeEntity_.premiumCurrency), "USD")
                    )
            );

    private final Predicate<Trade> appFilter;

    public ChallengeTradeFilter(TextCalc textCalc) {
        this.appFilter = trade -> !textCalc.areAnagrams(trade.buyerParty(), trade.sellerParty());
    }

    @Override
    public TradePredefinedFilterKind getKind() {
        return TradePredefinedFilterKind.CHALLENGE;
    }

    @Override
    public Specification<TradeEntity> getDbFilter() {
        return dbFilter;
    }

    @Override
    public Predicate<Trade> getAppFilter() {
        return appFilter;
    }
}

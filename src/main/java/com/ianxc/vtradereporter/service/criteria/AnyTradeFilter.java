package com.ianxc.vtradereporter.service.criteria;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component("any")
public class AnyTradeFilter implements TradeFilter {
    private static final Specification<TradeEntity> dbFilter = Specification.where(null);
    private static final Predicate<Trade> appFilter = (t -> true);

    @Override
    public TradePredefinedFilterKind getKind() {
        return TradePredefinedFilterKind.ANY;
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

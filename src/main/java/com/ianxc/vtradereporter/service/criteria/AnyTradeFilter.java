package com.ianxc.vtradereporter.service.criteria;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Predicate;

public enum AnyTradeFilter implements TradeFilter {
    INSTANCE;

    private static final Specification<TradeEntity> dbFilter = Specification.where(null);
    private static final Predicate<Trade> appFilter = (t -> true);

    @Override
    public Specification<TradeEntity> getDbFilter() {
        return dbFilter;
    }

    @Override
    public Predicate<Trade> getAppFilter() {
        return appFilter;
    }
}

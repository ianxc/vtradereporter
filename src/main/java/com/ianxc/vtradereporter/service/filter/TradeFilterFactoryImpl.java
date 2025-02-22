package com.ianxc.vtradereporter.service.filter;

import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class TradeFilterFactoryImpl implements TradeFilterFactory {
    private final Map<TradePredefinedFilterKind, TradeFilter> predefinedFilters;

    public TradeFilterFactoryImpl(List<TradeFilter> predefinedFilters) {
        var predefinedFiltersByType = new EnumMap<TradePredefinedFilterKind, TradeFilter>(TradePredefinedFilterKind.class);

        // check for duplicate filters
        for (var filter : predefinedFilters) {
            predefinedFiltersByType.compute(filter.getKind(), (kind, existingFilter) -> {
                if (existingFilter == null) return filter;
                throw new IllegalStateException(
                        String.format("got duplicate trade filter for kind=%s existingFilter=%s newFilter=%s",
                                kind, existingFilter, filter));
            });
        }
        this.predefinedFilters = predefinedFiltersByType;
    }

    @Override
    public TradeFilter getPredefinedFilter(TradePredefinedFilterKind kind) {
        return predefinedFilters.get(kind);
    }
}

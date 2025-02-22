package com.ianxc.vtradereporter.service.filter;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AnyTradeFilterTest {
    private final AnyTradeFilter filter = new AnyTradeFilter();

    @Test
    void when_getKind_then_shouldReturnAny() {
        assertThat(filter.getKind()).isEqualTo(TradePredefinedFilterKind.ANY);
    }

    @Test
    void when_applyAppFilter_then_shouldKeepAll() {
        final var startingTrades = List.of(
                new Trade(1L, "abc", "bac", new BigDecimal("1.23"), "AUD", Instant.EPOCH),
                new Trade(2L, "cab", "bac", new BigDecimal("2.34"), "NZD", Instant.MAX));

        final var filtered = startingTrades.stream()
                .filter(filter.getAppFilter())
                .toList();

        assertThat(filtered).isEqualTo(startingTrades);
    }
}

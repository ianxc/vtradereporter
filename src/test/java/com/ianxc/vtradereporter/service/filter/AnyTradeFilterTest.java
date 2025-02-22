package com.ianxc.vtradereporter.service.filter;

import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnyTradeFilterTest {
    private final AnyTradeFilter filter = new AnyTradeFilter();

    @Test
    void when_getKind_then_shouldReturnAny() {
        assertThat(filter.getKind()).isEqualTo(TradePredefinedFilterKind.ANY);
    }
}

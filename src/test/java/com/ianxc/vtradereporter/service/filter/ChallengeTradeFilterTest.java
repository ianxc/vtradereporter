package com.ianxc.vtradereporter.service.filter;

import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.util.TextCalc;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ChallengeTradeFilterTest {
    private final TextCalc textCalc = mock();
    private final ChallengeTradeFilter filter = new ChallengeTradeFilter(textCalc);

    @Test
    void when_getKind_then_shouldReturnAny() {
        assertThat(filter.getKind()).isEqualTo(TradePredefinedFilterKind.CHALLENGE);
    }
}

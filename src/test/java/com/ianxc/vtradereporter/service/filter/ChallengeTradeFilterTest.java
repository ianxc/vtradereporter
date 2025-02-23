package com.ianxc.vtradereporter.service.filter;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.util.TextCalc;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChallengeTradeFilterTest {
    private final TextCalc textCalc = mock();
    private final ChallengeTradeFilter filter = new ChallengeTradeFilter(textCalc);

    @Test
    void when_getKind_then_shouldReturnAny() {
        assertThat(filter.getKind()).isEqualTo(TradePredefinedFilterKind.CHALLENGE);
    }

    @Test
    void when_applyAppFilter_then_shouldOnlyKeepNonAnagrams() {
        final var nonAnagramTrade = new Trade(1L, "ABC_BANK", "EMU_BANK", BigDecimal.TEN, "AUD", Instant.EPOCH);
        final var anagramTrade = new Trade(2L, "KNAB_UME", "EMU_BANK", BigDecimal.TEN, "AUD", Instant.EPOCH);
        final var preAppFilterTrades = List.of(nonAnagramTrade, anagramTrade);
        when(textCalc.areAnagrams("ABC_BANK", "EMU_BANK")).thenReturn(false);
        when(textCalc.areAnagrams("KNAB_UME", "EMU_BANK")).thenReturn(true);

        final var postAppFilterTrades = preAppFilterTrades.stream().filter(filter.getAppFilter()).toList();

        assertThat(postAppFilterTrades).containsExactly(nonAnagramTrade);
    }
}

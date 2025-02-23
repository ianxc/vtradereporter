package com.ianxc.vtradereporter.service.query;

import com.ianxc.vtradereporter.mapper.TradeMapper;
import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.repo.TradeRepository;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import com.ianxc.vtradereporter.service.filter.TradeFilter;
import com.ianxc.vtradereporter.service.filter.TradeFilterFactory;
import com.ianxc.vtradereporter.service.filter.UnknownTradeFilterException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TradeQueryServiceImplTest {
    final TradeFilterFactory mockFilterFactory = mock();
    final TradeMapper mockMapper = mock();
    final TradeRepository mockRepository = mock();
    final TradeFilter mockFilter = mock();
    final Specification<TradeEntity> mockSpecification = mock();
    final TradeQueryService service = new TradeQueryServiceImpl(mockFilterFactory, mockMapper, mockRepository);

    @SuppressWarnings("unchecked")
    @Test
    void when_validFilterKind_then_returnFilteredTrades() {
        // Arrange
        final var entity1 = new TradeEntity(1L, "buyer1", "seller1", new BigDecimal("100"), "USD", Instant.EPOCH);
        final var entity2 = new TradeEntity(2L, "buyer2", "seller2", new BigDecimal("200"), "AUD", Instant.EPOCH);
        final var entities = List.of(entity1, entity2);

        final var trade1 = new Trade(1L, "buyer1", "seller1", new BigDecimal("100"), "USD", Instant.EPOCH);
        final var trade2 = new Trade(2L, "buyer2", "seller2", new BigDecimal("200"), "AUD", Instant.EPOCH);
        final Predicate<Trade> appFilter = trade -> trade.vgTradeId() == 1L;

        when(mockFilterFactory.getPredefinedFilter(TradePredefinedFilterKind.CHALLENGE)).thenReturn(mockFilter);
        when(mockFilter.getDbFilter()).thenReturn(mockSpecification);
        when(mockFilter.getAppFilter()).thenReturn(appFilter);
        // suppress unchecked cast due to type erase of Specification<TradeEntity>
        when(mockRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(entities);
        when(mockMapper.toModel(entity1)).thenReturn(trade1);
        when(mockMapper.toModel(entity2)).thenReturn(trade2);

        // Act
        final var result = service.getPrefilteredTrades(TradePredefinedFilterKind.CHALLENGE);

        // Assert
        assertThat(result).hasSize(1).containsExactly(trade1);
    }

    @Test
    void when_predefinedFilterKindHasNoImplementation_then_throwException() {
        // Arrange
        when(mockFilterFactory.getPredefinedFilter(any())).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> service.getPrefilteredTrades(TradePredefinedFilterKind.CHALLENGE))
                .isInstanceOf(UnknownTradeFilterException.class)
                .hasMessageContaining("filter for kind=CHALLENGE is not registered");
    }
}

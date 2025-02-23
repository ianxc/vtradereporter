package com.ianxc.vtradereporter.controller;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.model.api.TradeSubmission;
import com.ianxc.vtradereporter.service.query.TradeQueryService;
import com.ianxc.vtradereporter.service.submit.TradeSubmissionService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TradeReportControllerTest {
    final TradeQueryService mockQueryService = mock();
    final TradeSubmissionService mockSubmissionService = mock();
    final TradeReportController controller = new TradeReportController(mockQueryService, mockSubmissionService);

    @Test
    void when_getPrefilteredTrades_then_returnTrades() {
        // Arrange
        final var expectedTrades = List.of(
                new Trade(1L, "buyer1", "seller1", new BigDecimal("100"), "USD", null),
                new Trade(2L, "buyer2", "seller2", new BigDecimal("200"), "AUD", null)
        );
        when(mockQueryService.getPrefilteredTrades(TradePredefinedFilterKind.CHALLENGE)).thenReturn(expectedTrades);

        // Act
        final var result = controller.getPrefilteredTrades(TradePredefinedFilterKind.CHALLENGE);

        // Assert
        assertThat(result).isEqualTo(expectedTrades);
    }

    @Test
    void when_submitTrades_then_returnSubmission() {
        // Arrange
        final var expectedSubmission = new TradeSubmission(2);
        final var mockFiles = List.of(
                new MockMultipartFile("trade1.xml", "trade1.xml", "text/xml", "content1".getBytes()),
                new MockMultipartFile("trade2.xml", "trade2.xml", "text/xml", "content2".getBytes())
        );
        when(mockSubmissionService.submitTrades(any())).thenReturn(expectedSubmission);

        // Act
        final var result = controller.submitTrades(mockFiles);

        // Assert
        assertThat(result).isEqualTo(expectedSubmission);
        verify(mockSubmissionService).submitTrades(any());
    }

    @Test
    void when_submitBundledTrades_then_returnSubmission() {
        // Arrange
        final var expectedSubmission = new TradeSubmission(5);
        when(mockSubmissionService.submitBundledTrades()).thenReturn(expectedSubmission);

        // Act
        final var result = controller.submitBundledTrades();

        // Assert
        assertThat(result).isEqualTo(expectedSubmission);
        verify(mockSubmissionService).submitBundledTrades();
    }
}

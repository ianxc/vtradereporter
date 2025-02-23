package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.mapper.TradeMapper;
import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.repo.TradeRepository;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TradeSubmissionServiceImplTest {
    final BundledTradeLoader tradeLoader = mock();
    final TradeMapper tradeMapper = mock();
    final TradeRepository tradeRepository = mock();
    final XmlModelMapper xmlModelMapper = mock();
    final BiFunction<XPath, Document, Trade> tradeXmlExtractor = mock();

    final Trade trade1 = new Trade(null, "buyer1", "seller1", new BigDecimal("100"), "AUD", null);
    final Trade trade2 = new Trade(null, "buyer2", "seller2", new BigDecimal("200"), "USD", null);
    final TradeEntity tradeEntity1 = new TradeEntity();
    final TradeEntity tradeEntity2 = new TradeEntity();

    final InputStream inputStream1 = new ByteArrayInputStream("trade1".getBytes());
    final InputStream inputStream2 = new ByteArrayInputStream("trade2".getBytes());

    @Test
    void when_tradesSubmittedOk_then_returnCount() {
        // Arrange
        final var inputStreams = Stream.of(inputStream1, inputStream2);

        when(xmlModelMapper.extractModel(any(), any())).thenReturn(trade1, trade2);
        when(tradeMapper.toEntity(trade1)).thenReturn(tradeEntity1);
        when(tradeMapper.toEntity(trade2)).thenReturn(tradeEntity2);
        when(tradeRepository.saveAll(any())).thenReturn(List.of(tradeEntity1, tradeEntity2));

        final var service = new TradeSubmissionServiceImpl(
                tradeLoader,
                tradeMapper,
                tradeRepository,
                tradeXmlExtractor,
                xmlModelMapper
        );

        // Act
        final var result = service.submitTrades(inputStreams);

        // Assert
        assertThat(result.count()).isEqualTo(2);
    }

    @Test
    void when_submittingBundledTrades_then_returnSuccessfulSubmission() {
        // Arrange
        when(tradeLoader.loadBundledTrades()).thenReturn(Stream.of(inputStream1, inputStream2));
        when(xmlModelMapper.extractModel(any(), any())).thenReturn(trade1, trade2);
        when(tradeMapper.toEntity(trade1)).thenReturn(tradeEntity1);
        when(tradeMapper.toEntity(trade2)).thenReturn(tradeEntity2);
        when(tradeRepository.saveAll(any())).thenReturn(List.of(tradeEntity1, tradeEntity2));

        final var service = new TradeSubmissionServiceImpl(
                tradeLoader,
                tradeMapper,
                tradeRepository,
                tradeXmlExtractor,
                xmlModelMapper
        );

        // Act
        final var result = service.submitBundledTrades();

        // Assert
        assertThat(result.count()).isEqualTo(2);
    }
}

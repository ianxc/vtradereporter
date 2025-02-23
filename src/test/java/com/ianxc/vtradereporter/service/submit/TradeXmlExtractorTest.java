package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.model.api.Trade;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TradeXmlExtractorTest {
    final XPath mockXPath = mock();
    final Document mockDoc = mock();
    final TradeXmlExtractor tradeXmlExtractor = new TradeXmlExtractor();

    @Test
    void when_validTradeXml_then_extractsTradeCorrectly() throws XPathExpressionException {
        // Arrange
        when(mockXPath.evaluate("//buyerPartyReference/@href", mockDoc))
                .thenReturn("Buyer");
        when(mockXPath.evaluate("//sellerPartyReference/@href", mockDoc))
                .thenReturn("Seller");
        when(mockXPath.evaluate("//paymentAmount/amount", mockDoc))
                .thenReturn("1234.56");
        when(mockXPath.evaluate("//paymentAmount/currency", mockDoc))
                .thenReturn("AUD");

        // Act
        final var result = tradeXmlExtractor.apply(mockXPath, mockDoc);

        // Assert
        assertThat(result).isEqualTo(
                new Trade(null,
                        "Buyer",
                        "Seller",
                        new BigDecimal("1234.56"),
                        "AUD",
                        null));
    }

    @Test
    void when_xpathEvaluationFails_then_throwRuntimeException() throws XPathExpressionException {
        // Arrange
        when(mockXPath.evaluate(any(), eq(mockDoc)))
                .thenThrow(new XPathExpressionException("Test exception"));

        // Act & Assert
        assertThatThrownBy(() -> tradeXmlExtractor.apply(mockXPath, mockDoc))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasCauseExactlyInstanceOf(XPathExpressionException.class);
    }
}

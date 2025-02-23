package com.ianxc.vtradereporter.service.submit;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class XmlModelMapperTest {

    private final DocumentBuilderFactory mockDocBuilderFactory = mock();
    private final XPathFactory mockXPathFactory = mock();
    private final DocumentBuilder mockDocBuilder = mock();
    private final XPath mockXPath = mock();
    private final Document mockDoc = mock();

    private final XmlModelMapper xmlModelMapper = new XmlModelMapper(mockDocBuilderFactory, mockXPathFactory);

    @Test
    void when_noXmlExceptions_thenProduceTypedResult() throws Exception {
        // Arrange
        final var testXml = "<root><value>test</value></root>";
        final var inputStream = new ByteArrayInputStream(testXml.getBytes(StandardCharsets.UTF_8));

        when(mockDocBuilderFactory.newDocumentBuilder()).thenReturn(mockDocBuilder);
        when(mockDocBuilder.parse(any(InputStream.class))).thenReturn(mockDoc);
        when(mockXPathFactory.newXPath()).thenReturn(mockXPath);

        // Act
        final var result = xmlModelMapper.extractModel(inputStream, (xpath, doc) -> {
            assertThat(xpath).isSameAs(mockXPath);
            assertThat(doc).isSameAs(mockDoc);
            return "extracted value";
        });

        // Assert
        assertThat(result).isEqualTo("extracted value");
        verify(mockDocBuilderFactory).newDocumentBuilder();
        verify(mockDocBuilder).parse(inputStream);
        verify(mockXPathFactory).newXPath();
    }

    @Test
    void when_ParserConfigurationException_thenWrapInXmlModelMapperException() throws Exception {
        // Arrange
        when(mockDocBuilderFactory.newDocumentBuilder())
                .thenThrow(new ParserConfigurationException("Test exception"));

        final var inputStream = new ByteArrayInputStream("test".getBytes());

        // Act & Assert
        assertThatThrownBy(() -> xmlModelMapper.extractModel(inputStream, (xpath, doc) -> "value"))
                .isExactlyInstanceOf(XmlModelMapperException.class)
                .hasCauseExactlyInstanceOf(ParserConfigurationException.class);
    }

    @Test
    void when_SAXException_thenWrapInXmlModelMapperException() throws Exception {
        // Arrange
        when(mockDocBuilderFactory.newDocumentBuilder()).thenReturn(mockDocBuilder);
        when(mockDocBuilder.parse(any(InputStream.class)))
                .thenThrow(new SAXException("Test exception"));

        final var inputStream = new ByteArrayInputStream("test".getBytes());

        // Act & Assert
        assertThatThrownBy(() -> xmlModelMapper.extractModel(inputStream, (xpath, doc) -> "value"))
                .isExactlyInstanceOf(XmlModelMapperException.class)
                .hasCauseExactlyInstanceOf(SAXException.class);
    }

    @Test
    void when_IOException_thenWrapInXmlModelMapperException() throws Exception {
        // Arrange
        when(mockDocBuilderFactory.newDocumentBuilder()).thenReturn(mockDocBuilder);
        when(mockDocBuilder.parse(any(InputStream.class)))
                .thenThrow(new IOException("Test exception"));

        final var inputStream = new ByteArrayInputStream("test".getBytes());

        // Act & Assert
        assertThatThrownBy(() -> xmlModelMapper.extractModel(inputStream, (xpath, doc) -> "value"))
                .isExactlyInstanceOf(XmlModelMapperException.class)
                .hasCauseExactlyInstanceOf(IOException.class);
    }
}

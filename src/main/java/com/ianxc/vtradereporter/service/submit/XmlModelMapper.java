package com.ianxc.vtradereporter.service.submit;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiFunction;

@Component
public class XmlModelMapper {
    private final DocumentBuilderFactory docBuilderFactory;
    private final XPathFactory xpathFactory;

    public XmlModelMapper(DocumentBuilderFactory docBuilderFactory, XPathFactory xpathFactory) {
        this.docBuilderFactory = docBuilderFactory;
        this.xpathFactory = xpathFactory;
    }

    public <T> T extractModel(InputStream xmlStream, BiFunction<XPath, Document, T> extractor) {
        try {
            // init new doc builder and xpath as these aren't thread-safe.
            final var docBuilder = docBuilderFactory.newDocumentBuilder();
            final var doc = docBuilder.parse(xmlStream);
            final var xpath = xpathFactory.newXPath();
            return extractor.apply(xpath, doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // don't burden callers with a checked exception.
            throw new XmlModelMapperException(e);
        }
    }
}

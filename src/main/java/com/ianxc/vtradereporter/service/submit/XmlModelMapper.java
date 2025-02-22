package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.model.api.Trade;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@Component
public class XmlModelMapper {
    private final DocumentBuilderFactory docBuilderFactory;
    private final XPathFactory xpathFactory;

    public XmlModelMapper(DocumentBuilderFactory docBuilderFactory, XPathFactory xpathFactory) {
        this.docBuilderFactory = docBuilderFactory;
        this.xpathFactory = xpathFactory;
    }

    public Trade extractTrade(InputStream xmlStream) {
        try {
            // init new doc builder and xpath as these aren't thread-safe.
            final var docBuilder = docBuilderFactory.newDocumentBuilder();
            final var doc = docBuilder.parse(xmlStream);
            final var xpath = xpathFactory.newXPath();
            return new Trade(
                    null,
                    xpath.evaluate("//buyerPartyReference/@href", doc),
                    xpath.evaluate("//sellerPartyReference/@href", doc),
                    new BigDecimal(xpath.evaluate("//paymentAmount/amount", doc)),
                    xpath.evaluate("//paymentAMount/currency", doc),
                    null
            );
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            // don't burden callers with a checked exception.
            throw new XmlModelMapperException(e);
        }
    }
}

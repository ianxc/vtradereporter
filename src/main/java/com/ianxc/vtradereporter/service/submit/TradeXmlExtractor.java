package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.model.api.Trade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import java.math.BigDecimal;
import java.util.function.BiFunction;

@Component
@Qualifier("tradeXmlExtractor")
public class TradeXmlExtractor implements BiFunction<XPath, Document, Trade> {

    @Override
    public Trade apply(XPath xpath, Document doc) {
        try {
            return new Trade(
                    null,
                    xpath.evaluate("//buyerPartyReference/@href", doc),
                    xpath.evaluate("//sellerPartyReference/@href", doc),
                    new BigDecimal(xpath.evaluate("//paymentAmount/amount", doc)),
                    xpath.evaluate("//paymentAmount/currency", doc),
                    null
            );
        } catch (XPathExpressionException e) {
            throw new XmlExtractorException(e);
        }
    }
}

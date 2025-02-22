package com.ianxc.vtradereporter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

@Configuration
public class XmlConfig {
    @Bean
    public DocumentBuilderFactory documentBuilderFactory() throws ParserConfigurationException {
        // Calling factory.newDocumentBuilder() is thread-safe.
        // Therefore, inject a DocumentBuilderFactory as a singleton bean.
        // https://web.archive.org/web/20121214130319/http://jaxp.java.net/docs/spec/html/#plugabililty-thread-safety
        final var factory = DocumentBuilderFactory.newDefaultInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        return factory;
    }

    @Bean
    public XPathFactory xPathFactory() throws XPathFactoryConfigurationException {
        final var factory = XPathFactory.newDefaultInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        return factory;
    }
}

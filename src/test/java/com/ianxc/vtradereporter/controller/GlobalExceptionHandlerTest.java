package com.ianxc.vtradereporter.controller;

import com.ianxc.vtradereporter.service.filter.UnknownTradeFilterException;
import com.ianxc.vtradereporter.service.submit.XmlExtractorException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {
    final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void when_UnknownTradeFilterException_then_returnClientError() {
        final var pr = handler.handle(new UnknownTradeFilterException("hello"));

        assertThat(pr.getStatus()).isEqualTo(422);
        assertThat(pr.getDetail()).isEqualTo("hello");
    }

    @Test
    void when_XmlExtractorException_then_returnClientError() {
        final var pr = handler.handle(new XmlExtractorException(new RuntimeException()));

        assertThat(pr.getStatus()).isEqualTo(422);
        assertThat(pr.getDetail()).isEqualTo("Failed to parse XML data. Check that the contents conform to the required schema.");
    }
}

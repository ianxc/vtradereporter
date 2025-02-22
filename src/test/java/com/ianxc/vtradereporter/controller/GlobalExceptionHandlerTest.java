package com.ianxc.vtradereporter.controller;

import com.ianxc.vtradereporter.service.filter.UnknownTradeFilterException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {
    final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void when_UnknownTradeFilterException_then_returnClientError() {
        final var pr = handler.handleUnknownTradeFilterException(new UnknownTradeFilterException("hello"));

        assertThat(pr.getStatus()).isEqualTo(400);
        assertThat(pr.getDetail()).isEqualTo("hello");
    }
}

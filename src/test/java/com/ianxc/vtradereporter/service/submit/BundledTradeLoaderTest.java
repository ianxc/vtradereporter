package com.ianxc.vtradereporter.service.submit;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BundledTradeLoaderTest {

    @Test
    void when_resourcesExist_then_returnsStreamOfInputStreams() throws IOException {
        // Arrange
        final ResourcePatternResolver mockResolver = mock();
        final Resource mockResource1 = mock();
        final Resource mockResource2 = mock();
        final var inputStream1 = new ByteArrayInputStream("trade1".getBytes(StandardCharsets.UTF_8));
        final var inputStream2 = new ByteArrayInputStream("trade2".getBytes(StandardCharsets.UTF_8));

        when(mockResolver.getResources(anyString())).thenReturn(new Resource[]{mockResource1, mockResource2});
        when(mockResource1.getInputStream()).thenReturn(inputStream1);
        when(mockResource2.getInputStream()).thenReturn(inputStream2);

        final var loader = new BundledTradeLoader(mockResolver);

        // Act
        final var result = loader.loadBundledTrades();

        // Assert
        assertThat(result).hasSize(2);
    }

    @Test
    void when_resourceLoadingFails_then_throwsRuntimeException() throws IOException {
        // Arrange
        final ResourcePatternResolver mockResolver = mock();
        when(mockResolver.getResources(anyString())).thenThrow(new IOException("Test exception"));

        final var loader = new BundledTradeLoader(mockResolver);

        // Act & Assert
        assertThatThrownBy(loader::loadBundledTrades)
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(IOException.class);
    }
}

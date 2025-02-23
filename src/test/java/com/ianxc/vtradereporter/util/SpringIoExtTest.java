package com.ianxc.vtradereporter.util;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.InputStreamSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpringIoExtTest {

    @Test
    void when_validSource_then_returnInputStream() throws IOException {
        // Arrange
        final InputStreamSource mockSource = mock();
        final var expectedStream = new ByteArrayInputStream("test data".getBytes());
        when(mockSource.getInputStream()).thenReturn(expectedStream);

        // Act
        final var result = SpringIoExt.inputStreamOf(mockSource);

        // Assert
        assertThat(result).isSameAs(expectedStream);
    }

    @Test
    void when_sourceThrowsIOException_then_throwRuntimeException() throws IOException {
        // Arrange
        final InputStreamSource mockSource = mock();
        when(mockSource.getInputStream()).thenThrow(new IOException("Test exception"));

        // Act & Assert
        // noinspection resource
        assertThatThrownBy(() -> SpringIoExt.inputStreamOf(mockSource))
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(IOException.class);
    }
}

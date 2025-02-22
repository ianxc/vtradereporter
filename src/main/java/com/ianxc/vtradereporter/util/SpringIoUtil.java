package com.ianxc.vtradereporter.util;

import org.springframework.core.io.InputStreamSource;

import java.io.IOException;
import java.io.InputStream;

public class SpringIoUtil {
    public static InputStream inputStreamOf(InputStreamSource src) {
        try {
            return src.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

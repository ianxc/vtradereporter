package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.util.SpringIoExt;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class BundledTradeLoader {
    private final ResourcePatternResolver resourceResolver;
    private volatile Resource[] cachedResources;

    public BundledTradeLoader(ResourcePatternResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public Stream<InputStream> loadBundledTrades() {
        return Arrays.stream(getTradeResources()).map(SpringIoExt::inputStreamOf);
    }

    private Resource[] getTradeResources() {
        // correct double-checked locking implementation with volatile cachedResources
        var localResources = cachedResources;
        if (localResources == null) {
            synchronized (this) {
                localResources = cachedResources;
                if (localResources == null) {
                    try {
                        cachedResources = resourceResolver.getResources("classpath*:static/bundled-trades/*.xml");
                        localResources = cachedResources;
                    } catch (IOException e) {
                        // don't burden callers with checked exception
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return localResources;
    }
}

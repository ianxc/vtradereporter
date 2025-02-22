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

    public BundledTradeLoader(ResourcePatternResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public Stream<InputStream> loadBundledTrades() {
        final Resource[] tradeEventResources;
        try {
            tradeEventResources = resourceResolver.getResources("classpath*:static/bundled-trades/*.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Arrays.stream(tradeEventResources).map(SpringIoExt::inputStreamOf);
    }
}

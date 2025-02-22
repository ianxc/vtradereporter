package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.mapper.TradeMapper;
import com.ianxc.vtradereporter.repo.TradeRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class TradeSubmissionServiceImpl implements TradeSubmissionService {
    private final XmlModelMapper xmlModelMapper;
    private final TradeRepository tradeRepository;
    private final ResourcePatternResolver resourceResolver;
    private final TradeMapper tradeMapper;

    public TradeSubmissionServiceImpl(XmlModelMapper xmlModelMapper, TradeRepository tradeRepository,
                                      ResourcePatternResolver resourceResolver, TradeMapper tradeMapper) {
        this.xmlModelMapper = xmlModelMapper;
        this.tradeRepository = tradeRepository;
        this.resourceResolver = resourceResolver;
        this.tradeMapper = tradeMapper;
    }

    @Override
    public int submitBundledTrades() {
        final Resource[] tradeEventResources;
        try {
            tradeEventResources = resourceResolver.getResources("classpath*:static/bundled-trades/*.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final var trades = Arrays.stream(tradeEventResources)
                .parallel()
                .map(res -> {
                    try {
                        return res.getInputStream();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(xmlModelMapper::extractTrade)
                .map(tradeMapper::toEntity)
                .toList();

        tradeRepository.saveAll(trades);
        return trades.size();
    }
}

package com.ianxc.vtradereporter.service.submit;

import com.ianxc.vtradereporter.mapper.TradeMapper;
import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradeSubmission;
import com.ianxc.vtradereporter.repo.TradeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import java.io.InputStream;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@Service
public class TradeSubmissionServiceImpl implements TradeSubmissionService {
    private final BundledTradeLoader tradeLoader;
    private final TradeMapper tradeMapper;
    private final TradeRepository tradeRepository;
    private final XmlModelMapper xmlModelMapper;
    private final BiFunction<XPath, Document, Trade> tradeXmlExtractor;

    public TradeSubmissionServiceImpl(BundledTradeLoader tradeLoader, TradeMapper tradeMapper,
                                      TradeRepository tradeRepository,
                                      @Qualifier("tradeXmlExtractor") BiFunction<XPath, Document, Trade> tradeXmlExtractor,
                                      XmlModelMapper xmlModelMapper) {
        this.tradeLoader = tradeLoader;
        this.tradeMapper = tradeMapper;
        this.tradeRepository = tradeRepository;
        this.tradeXmlExtractor = tradeXmlExtractor;
        this.xmlModelMapper = xmlModelMapper;
    }

    @Override
    public TradeSubmission submitTrades(Stream<InputStream> tradeDataStreams) {
        return persistTrades(tradeDataStreams);
    }

    @Override
    public TradeSubmission submitBundledTrades() {
        final var tradeEvents = tradeLoader.loadBundledTrades();
        return persistTrades(tradeEvents);
    }

    private TradeSubmission persistTrades(Stream<InputStream> tradeDataStreams) {
        final var tradeEntities = tradeDataStreams.parallel()
                .map(tradeData -> xmlModelMapper.extractModel(tradeData, tradeXmlExtractor))
                .map(tradeMapper::toEntity)
                .toList();

        // CrudRepository methods are already marked with @Transactional and no other db operations are performed,
        // so all trades are written safely in one transaction.
        // Batches together writes for performance.
        tradeRepository.saveAll(tradeEntities);
        return new TradeSubmission(tradeEntities.size());
    }
}

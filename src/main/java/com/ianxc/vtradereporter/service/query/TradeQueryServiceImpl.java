package com.ianxc.vtradereporter.service.query;

import com.ianxc.vtradereporter.mapper.TradeMapper;
import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.repo.TradeRepository;
import com.ianxc.vtradereporter.repo.entity.TradeEntity_;
import com.ianxc.vtradereporter.service.criteria.TradeFilterFactory;
import com.ianxc.vtradereporter.service.criteria.UnknownTradeFilterException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeQueryServiceImpl implements TradeQueryService {
    private final TradeFilterFactory tradeFilterFactory;
    private final TradeMapper tradeMapper;
    private final TradeRepository tradeRepository;

    public TradeQueryServiceImpl(TradeFilterFactory tradeFilterFactory, TradeMapper tradeMapper,
                                 TradeRepository tradeRepository) {
        this.tradeFilterFactory = tradeFilterFactory;
        this.tradeMapper = tradeMapper;
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> getPrefilteredTrades(TradePredefinedFilterKind kind) {
        final var tradeFilter = tradeFilterFactory.getPredefinedFilter(kind);
        if (tradeFilter == null) {
            throw new UnknownTradeFilterException(String.format("filter for kind=%s is not registered", kind));
        }
        final var dbFilteredTrades = tradeRepository.findAll(
                tradeFilter.getDbFilter(),
                Sort.by(Order.desc(TradeEntity_.CREATE_TIME), Order.asc(TradeEntity_.ID)));

        return dbFilteredTrades.stream()
                .map(tradeMapper::toModel)
                .filter(tradeFilter.getAppFilter())
                .toList();
    }
}

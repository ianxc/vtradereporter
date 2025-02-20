package com.ianxc.vtradereporter.service;

import com.ianxc.vtradereporter.mapper.TradeMapper;
import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.repo.TradeRepository;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import com.ianxc.vtradereporter.repo.entity.TradeEntity_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeQueryServiceImpl implements TradeQueryService {
    private final TradeMapper tradeMapper;
    private final TradeRepository tradeRepository;

    public TradeQueryServiceImpl(TradeMapper tradeMapper, TradeRepository tradeRepository) {
        this.tradeMapper = tradeMapper;
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> getPrefilteredTrades() {
        final var dbFilteredTrades = tradeRepository.findAll(Spec.prefiltered);
        return dbFilteredTrades.stream()
                .map(tradeMapper::toModel)
                .toList();
    }
}

class Spec {
    static final Specification<TradeEntity> prefiltered = (root, query, builder) ->
            builder.or(
                    builder.and(
                            builder.equal(root.get(TradeEntity_.sellerParty), "EMU_BANK"),
                            builder.equal(root.get(TradeEntity_.premiumCurrency), "AUD")
                    ),
                    builder.and(
                            builder.equal(root.get(TradeEntity_.sellerParty), "BISON_BANK"),
                            builder.equal(root.get(TradeEntity_.premiumCurrency), "USD")
                    )
            );
}

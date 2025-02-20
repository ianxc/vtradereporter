package com.ianxc.vtradereporter.service;

import com.ianxc.vtradereporter.model.api.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeQueryServiceImpl implements TradeQueryService {
    @Override
    public List<Trade> getPrefilteredTrades() {
        return List.of();
    }
}

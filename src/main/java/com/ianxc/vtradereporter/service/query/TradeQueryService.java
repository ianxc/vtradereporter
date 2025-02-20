package com.ianxc.vtradereporter.service.query;

import com.ianxc.vtradereporter.model.api.Trade;

import java.util.List;

public interface TradeQueryService {
    List<Trade> getPrefilteredTrades();
}

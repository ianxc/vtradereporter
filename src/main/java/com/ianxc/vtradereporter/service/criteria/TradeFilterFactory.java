package com.ianxc.vtradereporter.service.criteria;

import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;

public interface TradeFilterFactory {
    /**
     * @param kind the predefined filter kind
     * @return the corresponding filter if it exists, else <code>null</code>
     */
    TradeFilter getPredefinedFilter(TradePredefinedFilterKind kind);
}

package com.ianxc.vtradereporter.model.api;

import java.math.BigDecimal;

public record Trade(String vgTradeId,
                    String buyerParty,
                    String sellerParty,
                    BigDecimal premiumAmount,
                    BigDecimal premiumCurrency) {
}

package com.ianxc.vtradereporter.model.api;

import java.math.BigDecimal;
import java.time.Instant;

public record Trade(String vgTradeId,
                    String buyerParty,
                    String sellerParty,
                    BigDecimal premiumAmount,
                    String premiumCurrency,
                    Instant recordedAt) {
}

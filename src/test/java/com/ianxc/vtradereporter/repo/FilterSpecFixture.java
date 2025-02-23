package com.ianxc.vtradereporter.repo;

import com.ianxc.vtradereporter.repo.entity.TradeEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class FilterSpecFixture {
    public static List<TradeEntity> defaultTradeEntities() {
        return List.of(
                new TradeEntity(
                        null,
                        "EMU_BANK",
                        "BISON_BANK",
                        new BigDecimal("600"),
                        "USD",
                        Instant.parse("2025-02-23T01:20:42.882462Z")
                ),
                new TradeEntity(
                        null,
                        "EMU_BANK",
                        "BISON_BANK",
                        new BigDecimal("150"),
                        "AUD",
                        Instant.parse("2025-02-23T01:20:42.882322Z")
                ),
                new TradeEntity(
                        null,
                        "EMU_BANK",
                        "BISON_BANK",
                        new BigDecimal("500"),
                        "USD",
                        Instant.parse("2025-02-23T01:20:42.882177Z")
                ),
                new TradeEntity(
                        null,
                        "KANMU_EB",
                        "EMU_BANK",
                        new BigDecimal("300"),
                        "AUD",
                        Instant.parse("2025-02-23T01:20:42.881940Z")
                ),
                new TradeEntity(
                        null,
                        "RIGHT_BANK",
                        "EMU_BANK",
                        new BigDecimal("100"),
                        "HKD",
                        Instant.parse("2025-02-23T01:20:42.881676Z")
                ),
                new TradeEntity(
                        null,
                        "EMU_BANK",
                        "EMU_BANK",
                        new BigDecimal("300"),
                        "AUD",
                        Instant.parse("2025-02-23T01:20:42.881540Z")
                ),
                new TradeEntity(
                        null,
                        "LEFT_BANK",
                        "EMU_BANK",
                        new BigDecimal("200"),
                        "AUD",
                        Instant.parse("2025-02-23T01:20:42.881346Z")
                ),
                new TradeEntity(
                        null,
                        "LEFT_BANK",
                        "EMU_BANK",
                        new BigDecimal("100"),
                        "AUD",
                        Instant.parse("2025-02-23T01:20:42.870934Z")
                )
        );
    }
}

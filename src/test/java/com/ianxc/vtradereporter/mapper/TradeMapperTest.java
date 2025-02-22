package com.ianxc.vtradereporter.mapper;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class TradeMapperTest {
    private final TradeMapper mapper = new TradeMapperImpl();

    @Test
    void toModel() {
        var entity = new TradeEntity();
        entity.setId(123L);
        entity.setBuyerParty("Buyer");
        entity.setSellerParty("Seller");
        entity.setPremiumAmount(new BigDecimal("987.65"));
        entity.setPremiumCurrency("AUD");
        entity.setCreateTime(Instant.EPOCH);

        var model = mapper.toModel(entity);

        assertThat(model).isEqualTo(new Trade(123L, "Buyer", "Seller", new BigDecimal("987.65"), "AUD", Instant.EPOCH));
    }

    @Test
    void toEntity() {
        var model = new Trade(456L, "Buyer", "Seller", new BigDecimal("987.65"), "AUD", Instant.EPOCH);

        var entity = mapper.toEntity(model);

        assertSoftly(sf -> {
            sf.assertThat(entity.getId()).isEqualTo(456L);
            sf.assertThat(entity.getBuyerParty()).isEqualTo("Buyer");
            sf.assertThat(entity.getSellerParty()).isEqualTo("Seller");
            sf.assertThat(entity.getPremiumAmount()).isEqualTo(new BigDecimal("987.65"));
            sf.assertThat(entity.getPremiumCurrency()).isEqualTo("AUD");
            sf.assertThat(entity.getCreateTime()).isEqualTo(Instant.EPOCH);
        });
    }
}

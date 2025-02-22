package com.ianxc.vtradereporter.repo;

import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaAuditing
@ActiveProfiles("test")
class TradeRepositoryTest {
    @Autowired
    TradeRepository tradeRepository;

    @Test
    void when_newEntities_then_saveThemAll() {
        final var tradeEntity1 = new TradeEntity();
        tradeEntity1.setBuyerParty("Buyer1");
        tradeEntity1.setSellerParty("Seller2");
        tradeEntity1.setPremiumAmount(new BigDecimal("1.23"));
        tradeEntity1.setPremiumCurrency("AUD");

        final var tradeEntity2 = new TradeEntity();
        tradeEntity2.setBuyerParty("Buyer2");
        tradeEntity2.setSellerParty("Seller1");
        tradeEntity2.setPremiumAmount(new BigDecimal("9.23"));
        tradeEntity2.setPremiumCurrency("NZD");

        final var result = tradeRepository.saveAll(List.of(tradeEntity1, tradeEntity2));

        assertThat(tradeRepository.count()).isEqualTo(2);
        assertThat(result).extracting(TradeEntity::getBuyerParty)
                .containsExactlyInAnyOrder("Buyer1", "Buyer2");
    }
}

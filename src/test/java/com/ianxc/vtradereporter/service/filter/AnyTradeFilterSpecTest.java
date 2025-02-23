package com.ianxc.vtradereporter.service.filter;

import com.ianxc.vtradereporter.repo.FilterSpecFixture;
import com.ianxc.vtradereporter.repo.TradeRepository;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaAuditing
@ActiveProfiles("test")
class AnyTradeFilterSpecTest {
    final AnyTradeFilter filter = new AnyTradeFilter();

    @Autowired
    TradeRepository tradeRepository;

    @Test
    void when_findEntities_then_returnAll() {
        final var tradeEntitiesToInsert = FilterSpecFixture.defaultTradeEntities();
        tradeRepository.saveAll(tradeEntitiesToInsert);

        final var result = tradeRepository.findAll(filter.getDbFilter());

        assertThat(result).extracting(TradeEntity::getId)
                .hasSize(tradeEntitiesToInsert.size());
    }
}

package com.ianxc.vtradereporter.service.filter;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradePredefinedFilterKind;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class TradeFilterFactoryImplTest {
    private static TradeFilterFactoryImpl newOkFactory() {
        return new TradeFilterFactoryImpl(List.of(
                new TradeFilter() {
                    @Override
                    public TradePredefinedFilterKind getKind() {
                        return TradePredefinedFilterKind.CHALLENGE;
                    }

                    @Override
                    public Specification<TradeEntity> getDbFilter() {
                        return null;
                    }

                    @Override
                    public Predicate<Trade> getAppFilter() {
                        return null;
                    }
                },
                new TradeFilter() {
                    @Override
                    public TradePredefinedFilterKind getKind() {
                        return TradePredefinedFilterKind.ANY;
                    }

                    @Override
                    public Specification<TradeEntity> getDbFilter() {
                        return null;
                    }

                    @Override
                    public Predicate<Trade> getAppFilter() {
                        return null;
                    }
                }
        ));
    }

    @Test
    void when_oneFilterPerKind_then_instantiate() {
        newOkFactory();
    }

    @Test
    void when_predefinedFilterKindHasFilter_then_getIt() {
        var factory = newOkFactory();

        var filter = factory.getPredefinedFilter(TradePredefinedFilterKind.ANY);

        assertThat(filter).isNotNull();
        assertThat(filter.getKind()).isEqualTo(TradePredefinedFilterKind.ANY);
    }

    @Test
    void when_predefinedFilterKindHasNoFilter_then_returnNull() {
        var factory = newOkFactory();

        var filter = factory.getPredefinedFilter(TradePredefinedFilterKind.UNREGISTERED);

        assertThat(filter).isNull();
    }

    @Test
    void when_duplicateFilterForKind_then_throw() {
        assertThatIllegalStateException().isThrownBy(() -> {
            var filter = new TradeFilter() {
                @Override
                public TradePredefinedFilterKind getKind() {
                    return TradePredefinedFilterKind.ANY;
                }

                @Override
                public Specification<TradeEntity> getDbFilter() {
                    return null;
                }

                @Override
                public Predicate<Trade> getAppFilter() {
                    return null;
                }
            };

            new TradeFilterFactoryImpl(List.of(filter, filter));
        });
    }
}

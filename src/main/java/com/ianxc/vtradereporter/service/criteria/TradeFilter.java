package com.ianxc.vtradereporter.service.criteria;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Predicate;

public interface TradeFilter {
    Specification<TradeEntity> getDbFilter();

    Predicate<Trade> getAppFilter();
}

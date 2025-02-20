package com.ianxc.vtradereporter.mapper;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    @Mapping(target = "vgTradeId", source = "id")
    @Mapping(target = "recordedAt", source = "createTime")
    Trade toModel(TradeEntity entity);
}

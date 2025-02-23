package com.ianxc.vtradereporter.repo;

import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long>, JpaSpecificationExecutor<TradeEntity> {
}

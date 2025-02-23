package com.ianxc.vtradereporter.repo;

import com.ianxc.vtradereporter.repo.entity.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long>, JpaSpecificationExecutor<TradeEntity> {
    @Transactional
    @Modifying
    @Query("update TradeEntity t set t.id = ?1 where t.id = ?2")
    int updateIdById(Long id, Long id1);
}

package com.ianxc.vtradereporter.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "trade")
@EntityListeners(AuditingEntityListener.class)
public class TradeEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buyer_party", nullable = false)
    private String buyerParty;

    @Column(name = "seller_party", nullable = false)
    private String sellerParty;

    @Column(name = "premium_amount", nullable = false)
    private BigDecimal premiumAmount;

    @Column(name = "premium_currency", nullable = false)
    private String premiumCurrency;

    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    @Override
    public String toString() {
        return "TradeEntity{" +
                "id=" + id +
                '}';
    }
}

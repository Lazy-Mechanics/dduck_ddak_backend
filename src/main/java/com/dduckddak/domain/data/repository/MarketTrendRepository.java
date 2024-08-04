package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.MarketTrends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketTrendRepository extends JpaRepository<MarketTrends, Integer>, MarketTrendRepositoryCustom {
}

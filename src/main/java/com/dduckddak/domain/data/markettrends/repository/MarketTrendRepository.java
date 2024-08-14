package com.dduckddak.domain.data.markettrends.repository;

import com.dduckddak.domain.data.markettrends.model.MarketTrends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketTrendRepository extends JpaRepository<MarketTrends, Integer>, MarketTrendRepositoryCustom {
}

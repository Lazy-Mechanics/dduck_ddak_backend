package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.MarketTrends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketTrendRepository extends JpaRepository<MarketTrends, Integer> {
    Optional<MarketTrends> findMarketTrendsByTownCodeAndQuarter(int townCode, int quarter);
}

package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.MarketTrends;

import java.util.List;
import java.util.Optional;

public interface MarketTrendRepositoryCustom {
    MarketTrends findMarketTrendsByTownCodeAndQuarter(int townCode, int quarter);
    List<MarketTrends> findMarketTrendsByTownCodeAndQuarterInDistrict(String district, int quarter);
}

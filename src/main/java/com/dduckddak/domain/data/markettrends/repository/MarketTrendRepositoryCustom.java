package com.dduckddak.domain.data.markettrends.repository;

import com.dduckddak.domain.data.markettrends.model.MarketTrends;

import java.util.List;

public interface MarketTrendRepositoryCustom {
    MarketTrends findMarketTrendsByTownCodeAndQuarter(int townCode, int quarter);
    List<MarketTrends> findMarketTrendsByTownCodeAndQuarterInDistrict(String district, int quarter);
}

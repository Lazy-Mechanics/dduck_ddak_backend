package com.dduckddak.domain.town.service;

import com.dduckddak.domain.data.dto.MarketTrendsResponse;
import com.dduckddak.domain.data.model.MarketTrends;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.data.repository.MarketTrendRepository;
import com.dduckddak.domain.data.repository.sales.SalesRepository;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryDto;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
import com.dduckddak.domain.town.repository.TownIndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TownIndustryService {
    private final TownIndustryRepository townIndustryRepository;
    private final MarketTrendRepository marketTrendRepository;
    private final SalesRepository salesRepository;

    public List<RecentlyTownIndustryDto> getRecentlyIndustries(int code, String name) {
        return townIndustryRepository.findTownIndustryByTownCodeAndQuarterAndName(code, name);
    }

    public List<SimilarTownIndustryDto> getSimilarIndustries(int code, String name) {
        return townIndustryRepository.findSimilarTownIndustryByTownCodeAndQuarterAndName(code, name);
    }

    public MarketTrendsResponse getIndustriesBusinessPeriod(int code, int quarter) {
        MarketTrends marketTrends = marketTrendRepository.findMarketTrendsByTownCodeAndQuarter(code, quarter);
        return new MarketTrendsResponse(marketTrends.getOperateSaleAvg());
    }

    public SalesResponse getIndustriesSales(int code, String name) {
        Sales sales = salesRepository.findByTownAndIndustry(code, name);
        return SalesResponse.of(sales);
    }
}

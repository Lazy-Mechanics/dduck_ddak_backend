package com.dduckddak.domain.town.service;

import com.dduckddak.domain.data.dto.MarketTrendsResponse;
import com.dduckddak.domain.data.model.MarketTrends;
import com.dduckddak.domain.data.repository.MarketTrendRepository;
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

    public List<RecentlyTownIndustryDto> getRecentlyIndustries(int code, String name) {
        return townIndustryRepository.findTownIndustryByTownCodeAndQuarterAndName(code, name);
    }

    public List<SimilarTownIndustryDto> getSimilarIndustries(int code, String name) {
        return townIndustryRepository.findSimilarTownIndustryByTownCodeAndQuarterAndName(code, name);
    }

    public MarketTrendsResponse getIndustriesBusinessPeriod(int code, int quarter) {
        MarketTrends marketTrends = marketTrendRepository.findMarketTrendsByTownCodeAndQuarter(code, quarter)
                .orElseThrow(() -> new IllegalArgumentException("해당 지역의 데이터가 존재하지 않습니다."));
        return new MarketTrendsResponse(marketTrends.getOperateSaleAvg());
    }
}

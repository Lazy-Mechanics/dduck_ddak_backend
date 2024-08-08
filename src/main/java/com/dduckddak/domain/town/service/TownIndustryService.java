package com.dduckddak.domain.town.service;

import com.dduckddak.domain.data.dto.MarketTrendsResponse;
import com.dduckddak.domain.data.model.MarketTrends;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.data.repository.MarketTrendRepository;
import com.dduckddak.domain.data.repository.sales.SalesRepository;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.SalesResponse;
import com.dduckddak.domain.town.dto.SalesVO;
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

    public List<RecentlyTownIndustryResponse> getRecentlyIndustries(int code, String name) {
        return townIndustryRepository.findTownIndustryByTownCodeAndQuarterAndName(code, name);
    }

    public List<RecentlyTownIndustryResponse> getRecentlyIndustriesInDistrict(String district, String name) {
        return townIndustryRepository.findTownIndustryByTownCodeAndQuarterAndNameInDistrict(district, name);
    }

    public List<SimilarTownIndustryDto> getSimilarIndustries(int code, String name) {
        return townIndustryRepository.findSimilarTownIndustryByTownCodeAndQuarterAndName(code, name);
    }

    public List<SimilarTownIndustryDto> getSimilarIndustriesInDistrict(String district, String name) {
        return townIndustryRepository.findSimilarTownIndustryByTownCodeAndQuarterAndNameInDistrict(district, name);
    }

    public MarketTrendsResponse getIndustriesBusinessPeriod(int code, int quarter) {
        MarketTrends marketTrends = marketTrendRepository.findMarketTrendsByTownCodeAndQuarter(code, quarter);
        return new MarketTrendsResponse(marketTrends.getOperateSaleAvg());
    }

    public MarketTrendsResponse getIndustriesBusinessPeriodInDistrict(String district, int quarter) {
        List<MarketTrends> marketTrendsList = marketTrendRepository.findMarketTrendsByTownCodeAndQuarterInDistrict(district, quarter);
        // average of operateSaleAvg
        Long operateSaleAvg = (long) marketTrendsList.stream()
                .mapToDouble(MarketTrends::getOperateSaleAvg)
                .average()
                .orElse(0);
        return new MarketTrendsResponse(operateSaleAvg);
    }

    public SalesResponse getIndustriesSales(int code, String name) {
        SalesVO salesVO = salesRepository.findByTownAndIndustry(code, name);
        return SalesResponse.of(salesVO, true);
    }

    public SalesResponse getIndustriesSalesInDistrict(String district, String name) {
        List<Sales> sales = salesRepository.findByTownAndIndustryInDistrict(district, name);
        return SalesResponse.of(new SalesVO(
                (long) sales.stream().mapToLong(Sales::getMondaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getTuesdaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getWednesdaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getThursdaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getFridaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getSaturdaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getSundaySales).average().orElse(0)
        ), true);
    }
}

package com.dduckddak.domain.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketAnalysisResponse {
    private String townName;
    private String industryName;
    private String quarter;
    private String currentQuarterSales;
    private String increaseRate;
    private String maxTimesSales;       // 최대 시간대
    private String maxDaySales;         // 최대 요일
    private Double floatingPopulationIncrease;  // 유동인구 증가율
    private Double workingPopulationIncrease;  // 직장인구 증가율
    private int newStores;      // 신규 상점 수
    private int closedStores;   // 폐업 상점 수
    private String agePopulation;   // 연령별 인구
    private String firstGender;
    private String secondGender;
    private String maleVsFemaleSales;   // 남녀 매출 비
}

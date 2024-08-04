package com.dduckddak.domain.data.model;

import com.dduckddak.domain.town.model.Town;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketTrends {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Town town;

    // 상품 변화 지표
    private String tradeAreaChangeIndex;

    // 상권 변화 지표 명
    private String areaChangeIndexName;

    // 운영 엽업 개월 평균
    private Long operateSaleAvg;

    // 폐업 영업 개월 평균
    private Long closeSaleAvg;

    // 운영 영업 개월 평균(서울)
    private Long operateSaleAvgBySeoul;

    // 폐업 영업 개월 평균(서울)
    private Long closeSaleAvgBySeoul;

    @Builder
    public MarketTrends(Town town, String tradeAreaChangeIndex, String areaChangeIndexName, Long operateSaleAvg,
                        Long closeSaleAvg, Long operateSaleAvgBySeoul, Long closeSaleAvgBySeoul) {
        this.town = town;
        this.tradeAreaChangeIndex = tradeAreaChangeIndex;
        this.areaChangeIndexName = areaChangeIndexName;
        this.operateSaleAvg = operateSaleAvg;
        this.closeSaleAvg = closeSaleAvg;
        this.operateSaleAvgBySeoul = operateSaleAvgBySeoul;
        this.closeSaleAvgBySeoul = closeSaleAvgBySeoul;
    }
}

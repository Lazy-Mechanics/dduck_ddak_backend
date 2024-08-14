package com.dduckddak.domain.data.sales.model;

import com.dduckddak.domain.town.model.TownIndustry;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long currentMonthlySales;   // 당월 매출

    private Long mondaySales;           // 월요일 매출
    private Long tuesdaySales;          // 화요일 매출
    private Long wednesdaySales;        // 수요일 매출
    private Long thursdaySales;         // 목요일 매출
    private Long fridaySales;           // 금요일 매출
    private Long saturdaySales;         // 토요일 매출
    private Long sundaySales;           // 일요일 매출

    private long weekdaySales;          // 주중 매출
    private long weekendSales;          // 주말 매출

    private Long hour_0_6;              // 0시 ~ 6시 매출
    private Long hour_6_11;             // 6시 ~ 11시 매출
    private Long hour_11_14;            // 11시 ~ 14시 매출
    private Long hour_14_17;            // 14시 ~ 17시 매출
    private Long hour_17_21;            // 17시 ~ 21시 매출
    private Long hour_21_24;            // 21시 ~ 24시 매출

    private Long menSales;              // 남성 매출
    private Long womenSales;            // 여성 매출

    private Long age10sSales;           // 10대 매출
    private Long age20sSales;           // 20대 매출
    private Long age30sSales;           // 30대 매출
    private Long age40sSales;           // 40대 매출
    private Long age50sSales;           // 50대 매출
    private Long age60sAndMoreSales;    // 60대 이상 매출

    @OneToOne(fetch = FetchType.LAZY)
    private TownIndustry townIndustry;  // 행정동 업종

    public Sales(Long currentMonthlySales, Long mondaySales, Long tuesdaySales, Long wednesdaySales, Long thursdaySales, Long fridaySales, Long saturdaySales, Long sundaySales, long weekdaySales, long weekendSales, Long hour_0_6, Long hour_6_11, Long hour_11_14, Long hour_14_17, Long hour_17_21, Long hour_21_24,
                 Long menSales, Long womenSales, Long age10sSales, Long age20sSales, Long age30sSales, Long age40sSales, Long age50sSales, Long age60sAndMoreSales, TownIndustry townIndustry) {
        this.currentMonthlySales = currentMonthlySales;
        this.mondaySales = mondaySales;
        this.tuesdaySales = tuesdaySales;
        this.wednesdaySales = wednesdaySales;
        this.thursdaySales = thursdaySales;
        this.fridaySales = fridaySales;
        this.saturdaySales = saturdaySales;
        this.sundaySales = sundaySales;
        this.weekdaySales = weekdaySales;
        this.weekendSales = weekendSales;
        this.hour_0_6 = hour_0_6;
        this.hour_6_11 = hour_6_11;
        this.hour_11_14 = hour_11_14;
        this.hour_14_17 = hour_14_17;
        this.hour_17_21 = hour_17_21;
        this.hour_21_24 = hour_21_24;
        this.menSales = menSales;
        this.womenSales = womenSales;
        this.age10sSales = age10sSales;
        this.age20sSales = age20sSales;
        this.age30sSales = age30sSales;
        this.age40sSales = age40sSales;
        this.age50sSales = age50sSales;
        this.age60sAndMoreSales = age60sAndMoreSales;
        this.townIndustry = townIndustry;
    }
}

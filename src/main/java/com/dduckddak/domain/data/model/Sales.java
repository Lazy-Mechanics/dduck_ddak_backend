package com.dduckddak.domain.data.model;

import com.dduckddak.domain.town.model.TownIndustry;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long currentMonthlySales;

    private Long mondaySales;
    private Long tuesdaySales;
    private Long wednesdaySales;
    private Long thursdaySales;
    private Long fridaySales;
    private Long saturdaySales;
    private Long sundaySales;

    private long weekdaySales;
    private long weekendSales;

    private Long hour_0_6;
    private Long hour_6_11;
    private Long hour_11_14;
    private Long hour_14_17;
    private Long hour_17_21;
    private Long hour_21_24;

    private Long menSales;
    private Long womenSales;

    private Long age10sSales;
    private Long age20sSales;
    private Long age30sSales;
    private Long age40sSales;
    private Long age50sSales;
    private Long age60sAndMoreSales;

    @OneToOne(fetch = FetchType.LAZY)
    private TownIndustry townIndustry;

    public Sales(Long age10sSales, Long age20sSales, Long age30sSales, Long age40sSales, Long age50sSales,
                 Long age60sAndMoreSales, Long currentMonthlySales, Long fridaySales,
                 Long hour_0_6, Long hour_11_14, Long hour_14_17, Long hour_17_21, Long hour_21_24, Long hour_6_11,
                 Long menSales, Long mondaySales, Long saturdaySales, Long sundaySales, Long thursdaySales, TownIndustry townIndustry,
                 Long tuesdaySales, Long wednesdaySales, long weekdaySales, long weekendSales, Long womenSales) {
        this.age10sSales = age10sSales;
        this.age20sSales = age20sSales;
        this.age30sSales = age30sSales;
        this.age40sSales = age40sSales;
        this.age50sSales = age50sSales;
        this.age60sAndMoreSales = age60sAndMoreSales;
        this.currentMonthlySales = currentMonthlySales;
        this.fridaySales = fridaySales;
        this.hour_0_6 = hour_0_6;
        this.hour_11_14 = hour_11_14;
        this.hour_14_17 = hour_14_17;
        this.hour_17_21 = hour_17_21;
        this.hour_21_24 = hour_21_24;
        this.hour_6_11 = hour_6_11;
        this.menSales = menSales;
        this.mondaySales = mondaySales;
        this.saturdaySales = saturdaySales;
        this.sundaySales = sundaySales;
        this.thursdaySales = thursdaySales;
        this.townIndustry = townIndustry;
        this.tuesdaySales = tuesdaySales;
        this.wednesdaySales = wednesdaySales;
        this.weekdaySales = weekdaySales;
        this.weekendSales = weekendSales;
        this.womenSales = womenSales;
    }
}

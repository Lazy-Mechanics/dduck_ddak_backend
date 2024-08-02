package com.dduckddak.domain.data.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sales {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}

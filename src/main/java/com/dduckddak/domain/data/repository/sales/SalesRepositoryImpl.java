package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.dto.QRecentlySalesDto;
import com.dduckddak.domain.data.dto.RecentlySalesDto;
import com.dduckddak.domain.data.model.Sales;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.dduckddak.domain.data.model.QSales.sales;
import static com.dduckddak.domain.town.model.QTown.town;
import static com.dduckddak.domain.town.model.QTownIndustry.townIndustry;

@RequiredArgsConstructor
public class SalesRepositoryImpl implements SalesRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public RecentlySalesDto findAllByTownCodeOrderByQuarterDesc(String code) {
        JPAQuery<RecentlySalesDto> query = queryFactory
                .select(new QRecentlySalesDto(
                        sales.hour_0_6.sum(),
                        sales.hour_6_11.sum(),
                        sales.hour_11_14.sum(),
                        sales.hour_14_17.sum(),
                        sales.hour_17_21.sum(),
                        sales.hour_21_24.sum(),
                        sales.townIndustry.town.quarter.stringValue()
                ))
                .from(sales)
                .join(sales.townIndustry, townIndustry)
                .join(sales.townIndustry.town, town)
                .where(sales.townIndustry.town.code.eq(code))
                .groupBy(sales.townIndustry.town.code, sales.townIndustry.town.quarter)
                .orderBy(sales.townIndustry.town.quarter.desc())
                .limit(1);
        return query.fetchOne();
    }

    @Override
    public Sales findByTownAndIndustry(int code, String name) {
        return queryFactory
                .selectFrom(sales)
                .join(sales.townIndustry, townIndustry).fetchJoin()
                .join(sales.townIndustry.town, town).fetchJoin()
                .where(
                        town.code.eq(String.valueOf(code)),
                        townIndustry.industry.name.eq(name),
                        town.quarter.eq(20241L)
                )
                .orderBy(town.quarter.desc())
                .fetchOne();
    }

}

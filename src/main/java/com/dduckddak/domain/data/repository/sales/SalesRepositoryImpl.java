package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.model.Sales;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.dduckddak.domain.data.model.QSales.sales;
import static com.dduckddak.domain.town.model.QTown.town;
import static com.dduckddak.domain.town.model.QTownIndustry.townIndustry;

@RequiredArgsConstructor
public class SalesRepositoryImpl implements SalesRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Sales> findTop1ByTownCodeOrderByQuarterDesc(String code) {
        JPAQuery<Sales> query = queryFactory
                .selectFrom(sales)
                .join(sales.townIndustry, townIndustry).fetchJoin()
                .join(sales.townIndustry.town, town).fetchJoin()
                .where(town.code.eq(code))
                .orderBy(town.quarter.desc())
                .limit(1);
        return Optional.ofNullable(query.fetchOne());
    }
}

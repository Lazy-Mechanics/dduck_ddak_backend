package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.MarketTrends;
import com.dduckddak.domain.data.model.QMarketTrends;
import com.dduckddak.domain.town.model.QTown;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.dduckddak.domain.data.model.QMarketTrends.*;
import static com.dduckddak.domain.town.model.QTown.*;

public class MarketTrendRepositoryImpl implements MarketTrendRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MarketTrendRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public MarketTrends findMarketTrendsByTownCodeAndQuarter(int townCode, int quarter) {
        return queryFactory
                .selectFrom(marketTrends)
                .where(
                        marketTrends.town.code.eq(String.valueOf(townCode)),
                        marketTrends.town.quarter.eq((long) quarter)
                )
                .fetchOne();
    }
}

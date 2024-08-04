package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Facility;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dduckddak.domain.data.model.QFacility.facility;
import static com.dduckddak.domain.data.model.QPopulation.population;
import static com.dduckddak.domain.town.model.QTown.town;

@RequiredArgsConstructor
public class FacilityRepositoryImpl implements FacilityRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Facility findByTownCodeAndQuarter(String code) {
        JPAQuery<Facility> query = queryFactory
                .selectFrom(facility)
                .join(facility.town, town).fetchJoin()
                .where(town.code.eq(code)
                        .and(town.quarter.eq(20241L))); // 최신데이터

        return query.fetchOne();
    }
}

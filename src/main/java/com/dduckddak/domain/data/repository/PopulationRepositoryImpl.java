package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dduckddak.domain.data.model.QPopulation.population;
import static com.dduckddak.domain.town.model.QTown.town;

@RequiredArgsConstructor
public class PopulationRepositoryImpl implements PopulationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Population> findTop5ByTownCodeAndPopulationTypeOrderByQuarterDesc(String code, PopulationType populationType) {
        JPAQuery<Population> query = queryFactory
                .selectFrom(population)
                .join(population.town, town).fetchJoin()
                .where(town.code.eq(code)
                        .and(population.populationType.eq(populationType)))
                .orderBy(town.quarter.desc())
                .limit(5);
        return query.fetch();
    }
}

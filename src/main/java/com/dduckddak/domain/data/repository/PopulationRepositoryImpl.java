package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.dto.PopulationByDistrictResponse;
import com.dduckddak.domain.data.dto.QPopulationByDistrictResponse;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.dduckddak.domain.town.dto.QRecentlyTownIndustryResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.dduckddak.domain.data.model.QPopulation.population;
import static com.dduckddak.domain.town.model.QTown.town;
import static com.dduckddak.domain.town.model.QTownIndustry.townIndustry;

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

    @Override
    public List<PopulationByDistrictResponse> findTop5ByDistrictAndPopulationTypeOrderByQuarterDesc(String district, PopulationType populationType) {
        return queryFactory
                .select(new QPopulationByDistrictResponse(
                        town.quarter.stringValue(),
                        population.totalPopulation.sum().stringValue()
                ))
                .from(population)
                .join(population.town, town)
                .where(
                        town.name.contains(district),
                        population.populationType.eq(populationType))
                .groupBy(town.quarter)
                .orderBy(town.quarter.desc())
                .limit(5)
                .fetch();

    }

    @Override
    public Optional<Population> findTop1ByTownCodeAndPopulationTypeOrderByQuarterDesc(String code, PopulationType populationType) {
        JPAQuery<Population> query = queryFactory
                .selectFrom(population)
                .join(population.town, town).fetchJoin()
                .where(town.code.eq(code)
                        .and(population.populationType.eq(populationType)))
                .orderBy(town.quarter.desc())
                .limit(1);
        return Optional.ofNullable(query.fetchOne());
    }


}

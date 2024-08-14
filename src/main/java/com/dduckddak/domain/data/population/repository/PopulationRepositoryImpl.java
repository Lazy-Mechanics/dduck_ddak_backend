package com.dduckddak.domain.data.population.repository;

import com.dduckddak.domain.data.population.dto.PopulationByDistrictResponse;
import com.dduckddak.domain.data.population.dto.PopulationsTop10Response;
import com.dduckddak.domain.data.population.dto.QPopulationByDistrictResponse;
import com.dduckddak.domain.data.population.model.Population;
import com.dduckddak.domain.data.population.model.PopulationType;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.dduckddak.domain.data.population.model.QPopulation.population;
import static com.dduckddak.domain.town.model.QTown.town;

@RequiredArgsConstructor
public class PopulationRepositoryImpl implements PopulationRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public List findPopulationsTop10(String selectCriteria, String orderCriteria, String populationType) {
        String queryString = "select \n" +
                "\tp20241.동이름 as townName,\n" +
                "\tp20241." + selectCriteria + " as populations,\n" +
                "\t(p20241." + selectCriteria + " - p20234." + selectCriteria + ") AS populationsDifference,\n" +
                "    CASE \n" +
                "        WHEN p20234." + selectCriteria + " != 0 THEN \n" +
                "            ((p20241." + selectCriteria + "  - p20234." + selectCriteria + ") / p20234." + selectCriteria + ") * 100\n" +
                "        ELSE \n" +
                "            NULL\n" +
                "    END AS increaseRate\n" +
                "from \n" +
                "(\n" +
                "\tselect t.name as 동이름, " + selectCriteria + " from population p\n" +
                "\tinner join town t on p.town_id = t.id\n" +
                "\twhere population_type = " + populationType + " and t.quarter = 20241\n" +
                ") p20241\n" +
                "left join\n" +
                "(\n" +
                "\tselect t.name as 동이름, " + selectCriteria + " from population p\n" +
                "\tinner join town t on p.town_id = t.id\n" +
                "\twhere population_type = " + populationType + " and t.quarter = 20234\n" +
                ") p20234\n" +
                "on \n" +
                "\tp20241.동이름 = p20234.동이름\n" +
                "order by " + orderCriteria + " desc " +
                "limit 10;";

        Query query = entityManager.createNativeQuery(queryString);

        List<Object[]> results = query.getResultList();
        List<PopulationsTop10Response> responseList = new ArrayList<>();

        for (Object[] result : results) {
            PopulationsTop10Response response = PopulationsTop10Response.builder()
                            .townName((String) result[0])
                            .populations(Long.parseLong(String.valueOf(result[1])))
                            .populationsDifference(Long.parseLong(String.valueOf(result[2])))
                            .increaseRate((float) (Math.round( Float.parseFloat(String.valueOf(result[3])) * 100 )/ 100.0))
                            .build();


            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<Population> findFloatingPopulationTransition(String code) {

        return queryFactory
                .selectFrom(population)
                .innerJoin(population.town, town).fetchJoin()
                .where(population.populationType.eq(PopulationType.FloatingPopulation)
                        .and(town.quarter.in(20241, 20234, 20233, 20232, 20231)))
                .orderBy(population.town.quarter.desc(), population.totalPopulation.desc())
                .fetch();

    }

}

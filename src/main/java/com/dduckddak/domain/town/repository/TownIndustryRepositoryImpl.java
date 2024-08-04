package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.dto.QRecentlyTownIndustryDto;
import com.dduckddak.domain.town.dto.QSimilarTownIndustryDto;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryDto;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.dduckddak.domain.town.model.QIndustry.*;
import static com.dduckddak.domain.town.model.QTown.*;
import static com.dduckddak.domain.town.model.QTownIndustry.*;

public class TownIndustryRepositoryImpl implements TownIndustryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public TownIndustryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RecentlyTownIndustryDto> findTownIndustryByTownCodeAndQuarterAndName(int code, String name) {
        return queryFactory
                .select(new QRecentlyTownIndustryDto(
                        town.quarter.stringValue(),
                        townIndustry.storeCount.stringValue()
                )).distinct()
                .from(townIndustry)
                .join(townIndustry.town, town)
                .join(townIndustry.industry, industry)
                .where(
                        town.code.eq(String.valueOf(code)),
                        industry.name.eq(name)
                )
                .orderBy(town.quarter.stringValue().desc())
                .limit(5)
                .fetch();
    }

    public List<SimilarTownIndustryDto> findSimilarTownIndustryByTownCodeAndQuarterAndName(int code, String name) {
        return queryFactory
                .select(new QSimilarTownIndustryDto(
                        town.quarter.stringValue(),
                        townIndustry.similarStoreCount.stringValue()
                )).distinct()
                .from(townIndustry)
                .join(townIndustry.town, town)
                .join(townIndustry.industry, industry)
                .where(
                        town.code.eq(String.valueOf(code)),
                        industry.name.eq(name)
                )
                .orderBy(town.quarter.stringValue().desc())
                .limit(5)
                .fetch();
    }

}
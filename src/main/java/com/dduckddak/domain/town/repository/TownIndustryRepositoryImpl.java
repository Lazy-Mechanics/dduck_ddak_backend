package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.dto.QRecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.QSimilarTownIndustryDto;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.dduckddak.domain.town.model.QIndustry.industry;
import static com.dduckddak.domain.town.model.QTown.town;
import static com.dduckddak.domain.town.model.QTownIndustry.townIndustry;

public class TownIndustryRepositoryImpl implements TownIndustryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public TownIndustryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RecentlyTownIndustryResponse> findTownIndustryByTownCodeAndQuarterAndName(int code, String name) {
        return queryFactory
                .select(new QRecentlyTownIndustryResponse(
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

    @Override
    public List<RecentlyTownIndustryResponse> findTownIndustryByTownCodeAndQuarterAndNameInDistrict(String district, String name) {
        return queryFactory
                .select(new QRecentlyTownIndustryResponse(
                        town.quarter.stringValue(),
                        townIndustry.storeCount.sum().stringValue()
                ))
                .from(townIndustry)
                .join(townIndustry.town, town)
                .join(townIndustry.industry, industry)
                .where(
                        town.name.contains(district),
                        industry.name.eq(name)
                )
                .groupBy(town.quarter)
                .orderBy(town.quarter.desc())
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
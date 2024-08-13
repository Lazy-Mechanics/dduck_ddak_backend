package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.data.dto.SalesTop10OfIndustryResponse;
import com.dduckddak.domain.data.dto.StoreCountTop10OfIndustryResponse;
import com.dduckddak.domain.data.dto.StoreCountTop10Response;
import com.dduckddak.domain.town.dto.QRecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.QSimilarTownIndustryDto;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

import static com.dduckddak.domain.town.model.QIndustry.industry;
import static com.dduckddak.domain.town.model.QTown.town;
import static com.dduckddak.domain.town.model.QTownIndustry.townIndustry;

public class TownIndustryRepositoryImpl implements TownIndustryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager entityManager;
    public TownIndustryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RecentlyTownIndustryResponse> findTownIndustryByTownCodeAndQuarterAndName(int code, String name) {
        return queryFactory
                .select(new QRecentlyTownIndustryResponse(
                        town.quarter.stringValue(),
                        townIndustry.storeCount.sum().stringValue()
                ))
                .from(townIndustry)
                .join(townIndustry.town, town)
                .join(townIndustry.industry, industry)
                .where(
                        town.code.eq(String.valueOf(code)),
                        industry.name.eq(name)
                )
                .groupBy(town.quarter)
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
                        townIndustry.similarStoreCount.sum().stringValue()
                ))
                .from(townIndustry)
                .join(townIndustry.town, town)
                .join(townIndustry.industry, industry)
                .where(
                        town.code.eq(String.valueOf(code)),
                        industry.name.eq(name)
                )
                .groupBy(town.quarter)
                .orderBy(town.quarter.stringValue().desc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<SimilarTownIndustryDto> findSimilarTownIndustryByTownCodeAndQuarterAndNameInDistrict(String district, String name) {
        return queryFactory
                .select(new QSimilarTownIndustryDto(
                        town.quarter.stringValue(),
                        townIndustry.similarStoreCount.sum().stringValue()
                ))
                .from(townIndustry)
                .join(townIndustry.town, town)
                .join(townIndustry.industry, industry)
                .where(
                        town.name.contains(district),
                        industry.name.eq(name)
                )
                .groupBy(town.quarter)
                .orderBy(town.quarter.stringValue().desc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<StoreCountTop10Response> findStoreCountTop10(String orderCriteria) {
        // 쿼리 문자열을 동적으로 생성
        String query = "select sc20241.행정동명 as townName, " +
                "sc20241.가게수 as storeCount20241, " +
                "sc20234.가게수 as storeCount20234, " +
                "sc20241.가게수 - sc20234.가게수 as storeCountDifference, " +
                "(sc20241.가게수 - sc20234.가게수) / sc20234.가게수 * 100 as increaseRate " +
                "from (select t.name as 행정동명, sum(ti.store_count) as 가게수 " +
                "from town_industry ti " +
                "join town t on ti.town_id = t.id " +
                "where t.quarter = 20241 " +
                "group by t.name, t.quarter) as sc20241 " +
                "left join (select t.name as 행정동명, sum(ti.store_count) as 가게수 " +
                "from town_industry ti " +
                "join town t on ti.town_id = t.id " +
                "where t.quarter = 20234 " +
                "group by t.name, t.quarter) as sc20234 " +
                "ON sc20234.행정동명 = sc20241.행정동명 " +
                "order by " + orderCriteria + " desc " +
                "limit 10";

        // Native query 생성 및 실행
        Query nativeQuery = entityManager.createNativeQuery(query);



        List<Object[]> results = nativeQuery.getResultList();
        List<StoreCountTop10Response> responseList = new ArrayList<>();

        for (Object[] result : results) {
            StoreCountTop10Response response = StoreCountTop10Response.builder()
                    .townName((String) result[0])
                    .storeCount20234(Long.parseLong(String.valueOf(result[1])))
                    .storeCount20241(Long.parseLong(String.valueOf(result[2])))
                    .storeCountDifference(Long.parseLong(String.valueOf(result[3])))
                    .increaseRate((float) (Math.round(Float.parseFloat(String.valueOf(result[4])) * 100) / 100.0))
                    .build();


            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<StoreCountTop10OfIndustryResponse> findStoreCountTop10OfIndustry(String industryName, String orderCriteria) {
        String query = "select sc20241.행정동명 as townName, " +
                "sc20241.업종명 as industryName, " +
                "sc20241.가게수 as storeCount20241, " +
                "sc20234.가게수 as storeCount20234, " +
                "sc20241.가게수 - sc20234.가게수 as storeCountDifference, " +
                "(sc20241.가게수 - sc20234.가게수) / sc20234.가게수 * 100 as increaseRate " +
                "from (select t.name as 행정동명, i.name as 업종명, ti.store_count as 가게수 " +
                "from town_industry ti " +
                "join town t on ti.town_id = t.id " +
                "join industry i on ti.industry_id = i.id " +
                "where t.quarter = 20241 and i.name = :industryName " +
                "group by t.name, t.quarter, i.id) as sc20241 " +
                "left join (select t.name as 행정동명, i.name as 업종명, ti.store_count as 가게수 " +
                "from town_industry ti " +
                "join town t on ti.town_id = t.id " +
                "join industry i on ti.industry_id = i.id " +
                "where t.quarter = 20234 and i.name = :industryName " +
                "group by t.name, t.quarter, i.id) as sc20234 " +
                "ON sc20234.행정동명 = sc20241.행정동명 AND sc20234.업종명 = sc20241.업종명 " +
                "order by " + orderCriteria + " desc " +
                "limit 10";

        // Native query 생성 및 실행
        Query nativeQuery = entityManager.createNativeQuery(query);

        // 파라미터 설정
        nativeQuery.setParameter("industryName", industryName);



        List<Object[]> results = nativeQuery.getResultList();
        List<StoreCountTop10OfIndustryResponse> responseList = new ArrayList<>();

        for (Object[] result : results) {
            StoreCountTop10OfIndustryResponse response = StoreCountTop10OfIndustryResponse.builder()
                    .townName((String) result[0])
                    .industryName(String.valueOf(result[1]))
                    .storeCount20234(Long.parseLong(String.valueOf(result[2])))
                    .storeCount20241(Long.parseLong(String.valueOf(result[3])))
                    .storeCountDifference(Long.parseLong(String.valueOf(result[4])))
                    .increaseRate((float) (Math.round(Float.parseFloat(String.valueOf(result[5])) * 100) / 100.0))
                    .build();


            responseList.add(response);
        }

        return responseList;
    }

}
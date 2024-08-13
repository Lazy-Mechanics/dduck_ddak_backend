package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.dto.*;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.town.dto.QSalesVO;
import com.dduckddak.domain.town.dto.SalesVO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.dduckddak.domain.data.model.QPopulation.population;
import static com.dduckddak.domain.data.model.QSales.sales;
import static com.dduckddak.domain.town.model.QTown.town;
import static com.dduckddak.domain.town.model.QTownIndustry.townIndustry;

@RequiredArgsConstructor
public class SalesRepositoryImpl implements SalesRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager entityManager;
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
    public SalesVO findByTownAndIndustry(int code, String name) {
        return queryFactory
                .select(new QSalesVO (
                        sales.mondaySales,
                        sales.tuesdaySales,
                        sales.wednesdaySales,
                        sales.thursdaySales,
                        sales.fridaySales,
                        sales.saturdaySales,
                        sales.sundaySales
                ))
                .from(sales)
                .join(sales.townIndustry, townIndustry)
                .join(townIndustry.town, town)
                .where(
                        town.code.eq(String.valueOf(code)),
                        townIndustry.industry.name.eq(name),
                        town.quarter.eq(20241L)
                )
                .orderBy(town.quarter.desc())
                .fetchOne();
    }

    @Override
    public List<Sales> findByTownAndIndustryInDistrict(String district, String name) {
        return queryFactory
                .selectFrom(sales)
                .join(sales.townIndustry, townIndustry).fetchJoin()
                .join(sales.townIndustry.town, town).fetchJoin()
                .where(
                        town.name.contains(district),
                        townIndustry.industry.name.eq(name),
                        town.quarter.eq(20241L)
                )
                .orderBy(town.quarter.desc())
                .fetch();
    }

    @Override
    public List<SalesForTransitionData> findSalesForTransitionData() {
        return queryFactory
                .select(new QSalesForTransitionData(
                        town.name.stringValue(),
                        town.code.stringValue(),
                        town.quarter,
                        sales.currentMonthlySales.sum()
                ))
                .from(townIndustry)
                .innerJoin(townIndustry.town, town)
                .innerJoin(sales).on(sales.townIndustry.eq(townIndustry))
                .where(
                    town.quarter.in(20241L, 20234L, 20233L, 20232L, 20231L)
                )
                .groupBy(town.name, town.quarter, town.code)
                .orderBy(town.quarter.desc(), sales.currentMonthlySales.sum().desc())
                .fetch();

    }

    @Override
    public List<SalesTop10Response> findSalesTop10(String orderCriteria) {
        String query = "SELECT " +
                "    sd2023.동이름 AS townName, " +
                "    sd2023.분기 AS quarter, " +
                "    sd2023.매출 AS sales20234, " +
                "    sd2024.매출 AS sales20241, " +
                "    (sd2024.매출 - sd2023.매출) AS salesDifference, " +
                "    CASE " +
                "        WHEN sd2023.매출 != 0 THEN " +
                "            ((sd2024.매출 - sd2023.매출) / sd2023.매출) * 100 " +
                "        ELSE " +
                "            NULL " +
                "    END AS increaseRate " +
                "FROM " +
                "    ( " +
                "        SELECT " +
                "            t.name AS 동이름, " +
                "            t.quarter AS 분기, " +
                "            SUM(s.current_monthly_sales) AS 매출 " +
                "        FROM " +
                "            town_industry ti " +
                "        INNER JOIN " +
                "            town t ON ti.town_id = t.id " +
                "        INNER JOIN " +
                "            industry i ON ti.industry_id = i.id " +
                "        INNER JOIN " +
                "            sales s ON ti.id = s.town_industry_id " +
                "        WHERE " +
                "            t.quarter = 20234 " +
                "        GROUP BY " +
                "            t.name, t.quarter " +
                "    ) sd2023 " +
                "LEFT JOIN " +
                "    ( " +
                "        SELECT " +
                "            t.name AS 동이름, " +
                "            t.quarter AS 분기, " +
                "            SUM(s.current_monthly_sales) AS 매출 " +
                "        FROM " +
                "            town_industry ti " +
                "        INNER JOIN " +
                "            town t ON ti.town_id = t.id " +
                "        INNER JOIN " +
                "            industry i ON ti.industry_id = i.id " +
                "        INNER JOIN " +
                "            sales s ON ti.id = s.town_industry_id " +
                "        WHERE " +
                "            t.quarter = 20241 " +
                "        GROUP BY " +
                "            t.name, t.quarter " +
                "    ) sd2024 " +
                "ON " +
                "    sd2023.동이름 = sd2024.동이름 " +
                "ORDER BY " + orderCriteria + " DESC " +
                "LIMIT 10";

        // Native query 생성 및 실행
        Query nativeQuery = entityManager.createNativeQuery(query);


        List<Object[]> results = nativeQuery.getResultList();
        List<SalesTop10Response> responseList = new ArrayList<>();

        for (Object[] result : results) {
            SalesTop10Response response = SalesTop10Response.builder()
                    .townName((String) result[0])
                    .quarter(Long.parseLong(String.valueOf(result[1])))
                    .sales20234(Long.parseLong(String.valueOf(result[2])))
                    .sales20241(Long.parseLong(String.valueOf(result[3])))
                    .salesDifference(Long.parseLong(String.valueOf(result[4])))
                    .increaseRate((float) (Math.round(Float.parseFloat(String.valueOf(result[5])) * 100) / 100.0))
                    .build();


            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public List<SalesTop10OfIndustryResponse> findSalesTop10OfIndustry(String name, String orderCriteria) {
        String query = "SELECT " +
                "    sd2023.동이름 AS townName, " +
                "    sd2023.업종명 AS industryName, " +
                "    sd2023.분기 AS quarter, " +
                "    sd2023.매출 AS sales20234, " +
                "    sd2024.매출 AS sales20241, " +
                "    (sd2024.매출 - sd2023.매출) AS salesDifference, " +
                "    CASE " +
                "        WHEN sd2023.매출 != 0 THEN " +
                "            ((sd2024.매출 - sd2023.매출) / sd2023.매출) * 100 " +
                "        ELSE " +
                "            NULL " +
                "    END AS increaseRate " +
                "FROM " +
                "    ( " +
                "        SELECT " +
                "            t.name AS 동이름, " +
                "            t.quarter AS 분기, " +
                "            i.name AS 업종명, " +
                "            SUM(s.current_monthly_sales) AS 매출 " +
                "        FROM " +
                "            town_industry ti " +
                "        INNER JOIN " +
                "            town t ON ti.town_id = t.id " +
                "        INNER JOIN " +
                "            industry i ON ti.industry_id = i.id " +
                "        INNER JOIN " +
                "            sales s ON ti.id = s.town_industry_id " +
                "        WHERE " +
                "            t.quarter = 20234 AND i.name = :name " +
                "        GROUP BY " +
                "            t.name, i.name, t.quarter " +
                "    ) sd2023 " +
                "LEFT JOIN " +
                "    ( " +
                "        SELECT " +
                "            t.name AS 동이름, " +
                "            t.quarter AS 분기, " +
                "            i.name AS 업종명, " +
                "            SUM(s.current_monthly_sales) AS 매출 " +
                "        FROM " +
                "            town_industry ti " +
                "        INNER JOIN " +
                "            town t ON ti.town_id = t.id " +
                "        INNER JOIN " +
                "            industry i ON ti.industry_id = i.id " +
                "        INNER JOIN " +
                "            sales s ON ti.id = s.town_industry_id " +
                "        WHERE " +
                "            t.quarter = 20241 AND i.name = :name " +
                "        GROUP BY " +
                "            t.name, i.name, t.quarter " +
                "    ) sd2024 " +
                "ON " +
                "    sd2023.동이름 = sd2024.동이름 " +
                "ORDER BY " + orderCriteria + " DESC " +
                "LIMIT 10";

        // Native query 생성 및 실행
        Query nativeQuery = entityManager.createNativeQuery(query);

        // 파라미터 설정
        nativeQuery.setParameter("name", name);



        List<Object[]> results = nativeQuery.getResultList();
        List<SalesTop10OfIndustryResponse> responseList = new ArrayList<>();

        for (Object[] result : results) {
            SalesTop10OfIndustryResponse response = SalesTop10OfIndustryResponse.builder()
                    .townName((String) result[0])
                    .industryName(String.valueOf(result[1]))
                    .quarter(Long.parseLong(String.valueOf(result[2])))
                    .sales20234(Long.parseLong(String.valueOf(result[3])))
                    .sales20241(Long.parseLong(String.valueOf(result[4])))
                    .salesDifference(Long.parseLong(String.valueOf(result[5])))
                    .increaseRate((float) (Math.round(Float.parseFloat(String.valueOf(result[6])) * 100) / 100.0))
                    .build();


            responseList.add(response);
        }

        return responseList;

    }
}

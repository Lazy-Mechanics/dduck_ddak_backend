package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.dto.SalesTop10OfIndustryResponse;
import com.dduckddak.domain.data.dto.SalesTop10Response;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.town.dto.SalesVO;
import com.dduckddak.domain.town.model.TownIndustry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer>, SalesRepositoryCustom {
    @Query("SELECT new com.dduckddak.domain.town.dto.SalesVO(" +
            "s1.mondaySales - s2.mondaySales, " +
            "s1.tuesdaySales - s2.tuesdaySales, " +
            "s1.wednesdaySales - s2.wednesdaySales, " +
            "s1.thursdaySales - s2.thursdaySales, " +
            "s1.fridaySales - s2.fridaySales, " +
            "s1.saturdaySales - s2.saturdaySales, " +
            "s1.sundaySales - s2.sundaySales) " +
            "FROM Sales s1, Sales s2 " +
            "JOIN s1.townIndustry ti1 " +
            "JOIN s2.townIndustry ti2 " +
            "JOIN ti1.town t1 " +
            "JOIN ti2.town t2 " +
            "JOIN ti1.industry i1 " +
            "JOIN ti2.industry i2 " +
            "WHERE t1.code = :code AND t1.quarter = :currentQuarter AND " +
            "t2.code = :code AND t2.quarter = :previousQuarter AND " +
            "i1.name = :name AND i2.name = :name")
    SalesVO getSalesCompare(@Param("code") String code,
                            @Param("name") String name,
                            @Param("currentQuarter") Long currentQuarter,
                            @Param("previousQuarter") Long previousQuarter);

    Sales findByTownIndustry(TownIndustry townIndustry);


    @Query(value = "SELECT \n" +
            "    sd2023.동이름 AS townName,\n" +
            "    sd2023.분기 AS quarter,\n" +
            "    sd2023.매출 AS sales20234, \n" +
            "    sd2024.매출 AS sales20241, \n" +
            "    (sd2024.매출 - sd2023.매출) AS salesDifference,\n" +
            "    CASE \n" +
            "        WHEN sd2023.매출 != 0 THEN \n" +
            "            ((sd2024.매출 - sd2023.매출) / sd2023.매출) * 100\n" +
            "        ELSE \n" +
            "            NULL\n" +
            "    END AS increaseRate\n" +
            "FROM \n" +
            "    (\n" +
            "        SELECT \n" +
            "            t.name AS 동이름, \n" +
            "            t.quarter AS 분기, \n" +
            "            SUM(s.current_monthly_sales) AS 매출\n" +
            "        FROM \n" +
            "            town_industry ti\n" +
            "        INNER JOIN \n" +
            "            town t ON ti.town_id = t.id\n" +
            "        INNER JOIN \n" +
            "            industry i ON ti.industry_id = i.id\n" +
            "        INNER JOIN \n" +
            "            sales s ON ti.id = s.town_industry_id\n" +
            "        WHERE \n" +
            "            t.quarter = 20234\n" +
            "        GROUP BY \n" +
            "            t.name, t.quarter\n" +
            "    ) sd2023\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            t.name AS 동이름, \n" +
            "            t.quarter AS 분기, \n" +
            "            SUM(s.current_monthly_sales) AS 매출\n" +
            "        FROM \n" +
            "            town_industry ti\n" +
            "        INNER JOIN \n" +
            "            town t ON ti.town_id = t.id\n" +
            "        INNER JOIN \n" +
            "            industry i ON ti.industry_id = i.id\n" +
            "        INNER JOIN \n" +
            "            sales s ON ti.id = s.town_industry_id\n" +
            "        WHERE \n" +
            "            t.quarter = 20241\n" +
            "        GROUP BY \n" +
            "            t.name, t.quarter\n" +
            "    ) sd2024 \n" +
            "ON \n" +
            "    sd2023.동이름 = sd2024.동이름\n" +
            "ORDER BY \n" +
            "    :orderCriteria DESC" +
            "    limit 10;", nativeQuery = true)
    List<SalesTop10Response> findSalesTop10(@Param("orderCriteria")String orderCriteria);


    @Query(value = "SELECT \n" +
            "    sd2023.동이름 AS townName,\n" +
            "\tsd2023.업종명 AS industryName,\n" +
            "    sd2023.분기 AS quarter,\n" +
            "    sd2023.매출 AS sales20234, \n" +
            "    sd2024.매출 AS sales20241, \n" +
            "    (sd2024.매출 - sd2023.매출) AS salesDifference,\n" +
            "    CASE \n" +
            "        WHEN sd2023.매출 != 0 THEN \n" +
            "            ((sd2024.매출 - sd2023.매출) / sd2023.매출) * 100\n" +
            "        ELSE \n" +
            "            NULL\n" +
            "    END AS increaseRate\n" +
            "FROM \n" +
            "    (\n" +
            "        SELECT \n" +
            "            t.name AS 동이름, \n" +
            "            t.quarter AS 분기,\n" +
            "            i.name AS 업종명,\n" +
            "            SUM(s.current_monthly_sales) AS 매출\n" +
            "        FROM \n" +
            "            town_industry ti\n" +
            "        INNER JOIN \n" +
            "            town t ON ti.town_id = t.id\n" +
            "        INNER JOIN \n" +
            "            industry i ON ti.industry_id = i.id\n" +
            "        INNER JOIN \n" +
            "            sales s ON ti.id = s.town_industry_id\n" +
            "        WHERE \n" +
            "            t.quarter = 20234 AND i.name = :name\n" +
            "        GROUP BY \n" +
            "            t.name, i.name, t.quarter\n" +
            "    ) sd2023\n" +
            "LEFT JOIN \n" +
            "    (\n" +
            "        SELECT \n" +
            "            t.name AS 동이름, \n" +
            "            t.quarter AS 분기, \n" +
            "            i.name 업종명,\n" +
            "            SUM(s.current_monthly_sales) AS 매출\n" +
            "        FROM \n" +
            "            town_industry ti\n" +
            "        INNER JOIN \n" +
            "            town t ON ti.town_id = t.id\n" +
            "        INNER JOIN \n" +
            "            industry i ON ti.industry_id = i.id\n" +
            "        INNER JOIN \n" +
            "            sales s ON ti.id = s.town_industry_id\n" +
            "        WHERE \n" +
            "            t.quarter = 20241 AND i.name = :name\n" +
            "        GROUP BY \n" +
            "            t.name, i.name, t.quarter\n" +
            "    ) sd2024 \n" +
            "ON \n" +
            "    sd2023.동이름 = sd2024.동이름\n" +
            "ORDER BY \n" +
            "    :orderCriteria DESC\n" +
            "limit 10;", nativeQuery = true)
    List<SalesTop10OfIndustryResponse> findSalesTop10OfIndustry(@Param("name")String name, @Param("orderCriteria")String orderCriteria);
}

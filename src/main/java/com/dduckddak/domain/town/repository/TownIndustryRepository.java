package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.data.dto.SalesDiffVO;
import com.dduckddak.domain.town.model.Industry;
import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.model.TownIndustry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TownIndustryRepository extends JpaRepository<TownIndustry, Integer>, TownIndustryRepositoryCustom {
    @Query(value =
            "SELECT \n" +
                    "    sd2023.동이름 as townName,\n" +
                    "    sd2024.분기 AS quarter,\n" +
                    "    sd2023.업종 as industryName,\n" +
                    "    sd2024.매출 AS currentQuarterSales,\n" +
                    "    CASE \n" +
                    "        WHEN sd2023.매출 != 0 THEN \n" +
                    "            ((sd2024.매출 - sd2023.매출) / sd2023.매출) * 100\n" +
                    "        ELSE \n" +
                    "            NULL\n" +
                    "    END AS increaseRate    \n" +
                    "FROM \n" +
                    "    (\n" +
                    "    SELECT \n" +
                    "    \tt.code AS 동코드,\n" +
                    "    \ti.name as 업종,\n" +
                    "        t.name AS 동이름, \n" +
                    "        t.quarter AS 분기, \n" +
                    "        s.current_monthly_sales AS 매출\n" +
                    "    FROM \n" +
                    "        dduckddak.town_industry ti\n" +
                    "    INNER JOIN \n" +
                    "        dduckddak.town t ON ti.town_id = t.id\n" +
                    "    INNER JOIN \n" +
                    "        dduckddak.industry i ON ti.industry_id = i.id\n" +
                    "    INNER JOIN \n" +
                    "        dduckddak.sales s ON ti.id = s.town_industry_id\n" +
                    "    WHERE \n" +
                    "        t.quarter = 20234 AND t.code = :code AND i.name = :name\n" +
                    "    ) as sd2023\n" +
                    "LEFT JOIN \n" +
                    "    (\n" +
                    "    SELECT \n" +
                    "    \tt.code AS 동코드,\n" +
                    "        t.name AS 동이름, \n" +
                    "        t.quarter AS 분기, \n" +
                    "        s.current_monthly_sales AS 매출\n" +
                    "    FROM \n" +
                    "        dduckddak.town_industry ti\n" +
                    "    INNER JOIN \n" +
                    "        dduckddak.town t ON ti.town_id = t.id\n" +
                    "    INNER JOIN \n" +
                    "        dduckddak.industry i ON ti.industry_id = i.id\n" +
                    "    INNER JOIN \n" +
                    "        dduckddak.sales s ON ti.id = s.town_industry_id\n" +
                    "    WHERE \n" +
                    "        t.quarter = 20241 AND t.code = :code AND i.name = :name\n" +
                    "    ) as sd2024 \n" +
                    "ON sd2023.동이름 = sd2024.동이름",
            nativeQuery = true)
    SalesDiffVO getTownSalesRawData(@Param(value = "code") Long code, @Param(value = "name") String name);
    TownIndustry findByTownAndIndustry(Town town, Industry industry);
}

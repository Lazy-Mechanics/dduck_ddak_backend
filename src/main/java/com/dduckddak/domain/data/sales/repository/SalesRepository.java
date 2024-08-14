package com.dduckddak.domain.data.sales.repository;

import com.dduckddak.domain.data.sales.dto.SalesRateByAgeAndIndustryResponse;
import com.dduckddak.domain.data.sales.dto.SalesRateByGenderAndIndustryResponse;
import com.dduckddak.domain.data.sales.model.Sales;
import com.dduckddak.domain.town.dto.SalesVO;
import com.dduckddak.domain.town.model.TownIndustry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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





    @Query(value =
            "SELECT \n" +
                    "\tt.name AS townName, \n" +
                    "    i.name as industryName,\n" +
                    "    ROUND(s.men_sales / (s.men_sales + s.women_sales) * 100, 1) AS menPercentage,\n" +
                    "    ROUND(s.women_sales / (s.men_sales + s.women_sales) * 100, 1) AS womenPercentage,\n" +
                    "    s.men_sales + s.women_sales as salesOfIndustry\n" +
                    "FROM \n" +
                    "\ttown_industry ti\n" +
                    "INNER JOIN \n" +
                    "\ttown t ON ti.town_id = t.id\n" +
                    "INNER JOIN \n" +
                    "\tindustry i ON ti.industry_id = i.id\n" +
                    "INNER JOIN \n" +
                    "\tsales s ON ti.id = s.town_industry_id\n" +
                    "WHERE \n" +
                    "\tt.quarter in (20241) AND t.code = :townCode AND i.name = :industryName",
            nativeQuery = true)
    SalesRateByGenderAndIndustryResponse findSalesRateByGenderAndIndustry(@Param("townCode") String townCode, @Param("industryName") String industryName);


    @Query(value =
            "SELECT \n" +
                    "\tt.name AS townName, \n" +
                    "    i.name as industryName,\n" +
                    "    age10s_sales + age20s_sales + age30s_sales + age40s_sales + age50s_sales + age60s_and_more_sales AS totalSales,\n" +
                    "\tROUND(age10s_sales / (age10s_sales + age20s_sales + age30s_sales + age40s_sales + age50s_sales + age60s_and_more_sales) * 100, 1) AS age10sSales,\n" +
                    "\tROUND(age20s_sales / (age10s_sales + age20s_sales + age30s_sales + age40s_sales + age50s_sales + age60s_and_more_sales) * 100, 1) AS age20sSales,\n" +
                    "\tROUND(age30s_sales / (age10s_sales + age20s_sales + age30s_sales + age40s_sales + age50s_sales + age60s_and_more_sales) * 100, 1) AS age30sSales,\n" +
                    "\tROUND(age40s_sales / (age10s_sales + age20s_sales + age30s_sales + age40s_sales + age50s_sales + age60s_and_more_sales) * 100, 1) AS age40sSales,\n" +
                    "\tROUND(age50s_sales / (age10s_sales + age20s_sales + age30s_sales + age40s_sales + age50s_sales + age60s_and_more_sales) * 100, 1) AS age50sSales,\n" +
                    "\tROUND(age60s_and_more_sales / (age10s_sales + age20s_sales + age30s_sales + age40s_sales + age50s_sales + age60s_and_more_sales) * 100, 1) AS age60sAndMoreSales,\n" +
                    "    ROUND(s.men_sales / (s.men_sales + s.women_sales) * 100, 1) AS menPercentage,\n" +
                    "    ROUND(s.women_sales / (s.men_sales + s.women_sales) * 100, 1) AS womenPercentage\n" +
                    "FROM \n" +
                    "\ttown_industry ti\n" +
                    "INNER JOIN \n" +
                    "\ttown t ON ti.town_id = t.id\n" +
                    "INNER JOIN \n" +
                    "\tindustry i ON ti.industry_id = i.id\n" +
                    "INNER JOIN \n" +
                    "\tsales s ON ti.id = s.town_industry_id\n" +
                    "WHERE \n" +
                    "\tt.quarter in (20241) AND t.code = :townCode AND i.name = :industryName ",
            nativeQuery = true)
    SalesRateByAgeAndIndustryResponse findSalesRateByAgeAndIndustry(@Param("townCode") String townCode, @Param("industryName") String industryName);
}

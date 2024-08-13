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



}

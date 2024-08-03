package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.Sales;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EstimateSalesBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Sales> sales) {
        String sql = "INSERT INTO sales (" +
                "age10s_sales, age20s_sales, age30s_sales, age40s_sales, " +
                "age50s_sales, age60s_and_more_sales, current_monthly_sales, " +
                "hour_0_6, hour_6_11, hour_11_14, hour_14_17, hour_17_21, hour_21_24, " +
                "men_sales, women_sales, " +
                "monday_sales, tuesday_sales, wednesday_sales, thursday_sales, friday_sales, saturday_sales, sunday_sales, " +
                "weekday_sales, weekend_sales, town_industry_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                sales,
                sales.size(),
                (PreparedStatement pstmt, Sales sale) -> {
                    pstmt.setLong(1, sale.getAge10sSales());
                    pstmt.setLong(2, sale.getAge20sSales());
                    pstmt.setLong(3, sale.getAge30sSales());
                    pstmt.setLong(4, sale.getAge40sSales());
                    pstmt.setLong(5, sale.getAge50sSales());
                    pstmt.setLong(6, sale.getAge60sAndMoreSales());
                    pstmt.setLong(7, sale.getCurrentMonthlySales());
                    pstmt.setLong(8, sale.getHour_0_6());
                    pstmt.setLong(9, sale.getHour_6_11());
                    pstmt.setLong(10, sale.getHour_11_14());
                    pstmt.setLong(11, sale.getHour_14_17());
                    pstmt.setLong(12, sale.getHour_17_21());
                    pstmt.setLong(13, sale.getHour_21_24());
                    pstmt.setLong(14, sale.getMenSales());
                    pstmt.setLong(15, sale.getWomenSales());
                    pstmt.setLong(16, sale.getMondaySales());
                    pstmt.setLong(17, sale.getTuesdaySales());
                    pstmt.setLong(18, sale.getWednesdaySales());
                    pstmt.setLong(19, sale.getThursdaySales());
                    pstmt.setLong(20, sale.getFridaySales());
                    pstmt.setLong(21, sale.getSaturdaySales());
                    pstmt.setLong(22, sale.getSundaySales());
                    pstmt.setLong(23, sale.getWeekdaySales());
                    pstmt.setLong(24, sale.getWeekendSales());
                    pstmt.setLong(25, sale.getTownIndustry().getId());
                });
    }
}

package com.dduckddak.domain.data.finance.repository;


import com.dduckddak.domain.data.finance.model.Finance;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FinanceBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Finance> financeList){

        String sql = "INSERT INTO finance (" +
                "average_monthly_income, clothing_expenditure, education_expenditure, entertainment_expenditure," +
                " food_expenditure, grocery_expenditure, household_goods_expenditure, leisure_culture_expenditure," +
                " medical_expenditure, total_expenditure, town_id, transportation_expenditure) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                financeList,
                financeList.size(),
                (PreparedStatement ps, Finance finance) -> {
                    ps.setLong(1, finance.getAverageMonthlyIncome());
                    ps.setLong(2, finance.getClothingExpenditure());
                    ps.setLong(3, finance.getEducationExpenditure());
                    ps.setLong(4, finance.getEntertainmentExpenditure());
                    ps.setLong(5, finance.getFoodExpenditure());
                    ps.setLong(6, finance.getGroceryExpenditure());
                    ps.setLong(7, finance.getHouseholdGoodsExpenditure());
                    ps.setLong(8, finance.getLeisureCultureExpenditure());
                    ps.setLong(9, finance.getMedicalExpenditure());
                    ps.setLong(10, finance.getTotalExpenditure());
                    ps.setLong(11, finance.getTown().getId());
                    ps.setLong(12, finance.getTransportationExpenditure());
                });

    }

}

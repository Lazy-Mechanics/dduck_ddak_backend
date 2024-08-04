package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Facility;
import com.dduckddak.domain.data.model.MarketTrends;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarketTrendsBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<MarketTrends> marketTrends) {
        String sql = "INSERT INTO market_trends (" +
                "trade_area_change_index, area_change_index_name, operate_sale_avg, close_sale_avg, " +
                "operate_sale_avg_by_seoul, close_sale_avg_by_seoul)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                marketTrends,
                marketTrends.size(),
                (PreparedStatement pstmt, MarketTrends marketTrend) -> {
                    pstmt.setString(1, marketTrend.getTradeAreaChangeIndex());
                    pstmt.setString(2, marketTrend.getAreaChangeIndexName());
                    pstmt.setLong(3, marketTrend.getOperateSaleAvg());
                    pstmt.setLong(4, marketTrend.getCloseSaleAvg());
                    pstmt.setLong(5, marketTrend.getOperateSaleAvgBySeoul());
                    pstmt.setLong(6, marketTrend.getCloseSaleAvgBySeoul());
                });
    }
}

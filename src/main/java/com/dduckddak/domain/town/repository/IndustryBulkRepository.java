package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.model.Industry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IndustryBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Industry> industries) {
        String sql = "INSERT INTO industry (" +
                "code, name)" +
                "VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql,
                industries,
                industries.size(),
                (PreparedStatement pstmt, Industry industry) -> {
                    pstmt.setString(1, industry.getCode());
                    pstmt.setString(2, industry.getName());
                });
    }
}

package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.model.Town;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TownBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Town> townList){

        String sql = "INSERT INTO town (" +
                "quarter, area, code, name) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                townList,
                townList.size(),
                (PreparedStatement ps, Town town) -> {
                    ps.setString(1, town.getQuarter().toString());
                    ps.setString(2, town.getArea());
                    ps.setString(3, town.getCode());
                    ps.setString(4, town.getName());
                });

    }
}

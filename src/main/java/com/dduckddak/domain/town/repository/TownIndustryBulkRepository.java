package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.model.TownIndustry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TownIndustryBulkRepository {

    private final JdbcTemplate jdbcTemplate;


    @Transactional
    public void saveAll(List<TownIndustry> townIndustryList){

        String sql = "INSERT INTO town_industry (" +
                "industry_id, town_id, store_count, similar_store_count, open_store_count, close_store_count) " +
                "VALUES (?, ?, ?, ?, ?, ?) ";

        jdbcTemplate.batchUpdate(sql,
                townIndustryList,
                townIndustryList.size(),
                (PreparedStatement ps, TownIndustry townIndustry) -> {
                    ps.setLong(1, townIndustry.getIndustry().getId());
                    ps.setLong(2, townIndustry.getTown().getId());
                    ps.setLong(3, townIndustry.getStoreCount());
                    ps.setLong(4, townIndustry.getSimilarStoreCount());
                    ps.setLong(5, townIndustry.getOpenStoreCount());
                    ps.setLong(6, townIndustry.getCloseStoreCount());
                });
    }
}

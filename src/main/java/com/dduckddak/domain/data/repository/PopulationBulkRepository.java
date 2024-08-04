package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PopulationBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Population> populationList){

        String sql = "INSERT INTO population (" +
                "population_type, total_population, men_population, women_population, " +
                "age10s_population, age20s_population, age30s_population, age40s_population, " +
                "age50s_population, age60s_and_more_population, monday_population, tuesday_population, " +
                "wednesday_population, thursday_population, friday_population, saturday_population, " +
                "sunday_population, hour_0_6, hour_6_11, hour_11_14, hour_14_17, hour_17_21, hour_21_24, town_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                populationList,
                populationList.size(),
                (PreparedStatement ps, Population population) -> {
                    ps.setString(1, population.getPopulationType().toString());
                    ps.setLong(2, population.getTotalPopulation());
                    ps.setLong(3, population.getMenPopulation());
                    ps.setLong(4, population.getWomenPopulation());
                    ps.setLong(5, population.getAge10sPopulation());
                    ps.setLong(6, population.getAge20sPopulation());
                    ps.setLong(7, population.getAge30sPopulation());
                    ps.setLong(8, population.getAge40sPopulation());
                    ps.setLong(9, population.getAge50sPopulation());
                    ps.setLong(10, population.getAge60sAndMorePopulation());
                    ps.setLong(11, population.getMondayPopulation());
                    ps.setLong(12, population.getTuesdayPopulation());
                    ps.setLong(13, population.getWednesdayPopulation());
                    ps.setLong(14, population.getThursdayPopulation());
                    ps.setLong(15, population.getFridayPopulation());
                    ps.setLong(16, population.getSaturdayPopulation());
                    ps.setLong(17, population.getSundayPopulation());
                    ps.setLong(18, population.getHour_0_6());
                    ps.setLong(19, population.getHour_6_11());
                    ps.setLong(20, population.getHour_11_14());
                    ps.setLong(21, population.getHour_14_17());
                    ps.setLong(22, population.getHour_17_21());
                    ps.setLong(23, population.getHour_21_24());
                    ps.setLong(24, population.getTown().getId());
                });
    }

    @Transactional
    public void saveAll(List<Population> populationList, PopulationType PopulationType){

        String sql = "INSERT INTO population (" +
                "population_type, total_population, men_population, women_population, " +
                "age10s_population, age20s_population, age30s_population, age40s_population, " +
                "age50s_population, age60s_and_more_population, town_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                populationList,
                populationList.size(),
                (PreparedStatement ps, Population population) -> {
                    ps.setString(1, population.getPopulationType().toString());
                    ps.setLong(2, population.getTotalPopulation());
                    ps.setLong(3, population.getMenPopulation());
                    ps.setLong(4, population.getWomenPopulation());
                    ps.setLong(5, population.getAge10sPopulation());
                    ps.setLong(6, population.getAge20sPopulation());
                    ps.setLong(7, population.getAge30sPopulation());
                    ps.setLong(8, population.getAge40sPopulation());
                    ps.setLong(9, population.getAge50sPopulation());
                    ps.setLong(10, population.getAge60sAndMorePopulation());
                    ps.setLong(11, population.getTown().getId());
                });
    }
}

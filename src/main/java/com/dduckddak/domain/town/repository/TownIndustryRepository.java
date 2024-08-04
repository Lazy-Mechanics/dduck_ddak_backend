package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.model.TownIndustry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TownIndustryRepository extends JpaRepository<TownIndustry, Integer>, TownIndustryRepositoryCustom {
    @Query
}

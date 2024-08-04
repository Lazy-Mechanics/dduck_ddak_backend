package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.dto.RecentlyTownIndustryDto;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;

import java.util.List;

public interface TownIndustryRepositoryCustom {
    List<RecentlyTownIndustryDto> findTownIndustryByTownCodeAndQuarterAndName(int code, String name);
    List<SimilarTownIndustryDto> findSimilarTownIndustryByTownCodeAndQuarterAndName(int code, String name);
}

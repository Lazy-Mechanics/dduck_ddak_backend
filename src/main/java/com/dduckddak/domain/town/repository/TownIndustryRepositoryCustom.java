package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.dto.RecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;

import java.util.List;

public interface TownIndustryRepositoryCustom {
    List<RecentlyTownIndustryResponse> findTownIndustryByTownCodeAndQuarterAndName(int code, String name);
    List<RecentlyTownIndustryResponse> findTownIndustryByTownCodeAndQuarterAndNameInDistrict(String district, String name);
    List<SimilarTownIndustryDto> findSimilarTownIndustryByTownCodeAndQuarterAndName(int code, String name);
}

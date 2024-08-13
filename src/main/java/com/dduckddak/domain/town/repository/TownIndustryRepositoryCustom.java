package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.data.dto.StoreCountTop10OfIndustryResponse;
import com.dduckddak.domain.data.dto.StoreCountTop10Response;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TownIndustryRepositoryCustom {
    List<RecentlyTownIndustryResponse> findTownIndustryByTownCodeAndQuarterAndName(int code, String name);
    List<RecentlyTownIndustryResponse> findTownIndustryByTownCodeAndQuarterAndNameInDistrict(String district, String name);
    List<SimilarTownIndustryDto> findSimilarTownIndustryByTownCodeAndQuarterAndName(int code, String name);
    List<SimilarTownIndustryDto> findSimilarTownIndustryByTownCodeAndQuarterAndNameInDistrict(String district, String name);


    List<StoreCountTop10Response> findStoreCountTop10(String orderCriteria);

    List<StoreCountTop10OfIndustryResponse> findStoreCountTop10OfIndustry(String industryName, String orderCriteria);
}

package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.dto.PopulationByDistrictResponse;
import com.dduckddak.domain.data.dto.PopulationsTop10Response;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;

import java.util.List;
import java.util.Optional;

public interface PopulationRepositoryCustom {

    // 동 코드를 입력 받으면 분기별 유동인구를 반환 top5
    List<Population> findTop5ByTownCodeAndPopulationTypeOrderByQuarterDesc(String code, PopulationType populationType);

    // 자치구 명을 입력 받으면 분기별 유동인구를 반환 top5
    List<PopulationByDistrictResponse> findTop5ByDistrictAndPopulationTypeOrderByQuarterDesc(String district, PopulationType populationType);

    // 최신 분기의 시간당 유동인구를 반환
    Optional<Population> findTop1ByTownCodeAndPopulationTypeOrderByQuarterDesc(String code, PopulationType populationType);


    List<PopulationsTop10Response> findPopulationsTop10(String selectCriteria, String orderCriteria, String populationType);
    }

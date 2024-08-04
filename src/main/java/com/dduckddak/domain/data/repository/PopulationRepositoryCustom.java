package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;

import java.util.List;

public interface PopulationRepositoryCustom {

    // 동 코드를 입력 받으면 분기별 유동인구를 반환 top5
    List<Population> findTop5ByTownCodeAndPopulationTypeOrderByQuarterDesc(String code, PopulationType populationType);

}

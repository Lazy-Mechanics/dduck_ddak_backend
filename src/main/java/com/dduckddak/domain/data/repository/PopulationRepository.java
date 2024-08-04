package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopulationRepository extends JpaRepository<Population, Long> {

    // 동 코드를 입력 받으면 분기별 유동인구를 반환 top5
    List<Population> findTop5ByTownCodeAAndPopulationTypeOrderByQuarterDesc(String code, PopulationType populationType);
}

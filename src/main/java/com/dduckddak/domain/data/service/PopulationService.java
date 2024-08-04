package com.dduckddak.domain.data.service;

import com.dduckddak.domain.data.dto.FloatingPopulationByQuarterDto;
import com.dduckddak.domain.data.dto.FloatingPopulationByQuarterDto.QuarterPopulationDto;
import com.dduckddak.domain.data.dto.FloatingPopulationByTimeDto;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.dduckddak.domain.data.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PopulationService {

    private final PopulationRepository populationRepository;

    //동 코드를 입력 받으면 분기별 유동인구를 반환
    public FloatingPopulationByQuarterDto getFloatingPopulationByCodeTop5(String code) {
        List<Population> populations = populationRepository.findTop5ByTownCodeAndPopulationTypeOrderByQuarterDesc(code, PopulationType.FloatingPopulation);
        return FloatingPopulationByQuarterDto.from(populations);
    }
}

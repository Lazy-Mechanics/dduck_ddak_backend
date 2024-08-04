package com.dduckddak.domain.data.service;

import com.dduckddak.domain.data.dto.PopulationByQuarterDto;
import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.dduckddak.domain.data.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PopulationService {

    private final PopulationRepository populationRepository;

    //동 코드와 인구타입을 입력 받으면 분기별 유동인구를 반환
    public PopulationByQuarterDto getPopulationByCodeTop5(String code, PopulationType floatingPopulation) {
        List<Population> populations = populationRepository.findTop5ByTownCodeAndPopulationTypeOrderByQuarterDesc(code, floatingPopulation);
        return PopulationByQuarterDto.from(populations);
    }

    //최신 분기의 시간당 유동인구를 반환
    public TimelyDto getFloatingPopulationByCodeTop1(String code) {
        Population population = populationRepository.findTop1ByTownCodeAndPopulationTypeOrderByQuarterDesc(code, PopulationType.FloatingPopulation)
                .orElseThrow(() -> new IllegalArgumentException("해당 동의 유동인구 데이터가 존재하지 않습니다."));
        return TimelyDto.from(population);
    }


}

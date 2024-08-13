package com.dduckddak.domain.data.service;

import com.dduckddak.domain.data.dto.PopulationByDistrictResponse;
import com.dduckddak.domain.data.dto.PopulationByQuarterDto;
import com.dduckddak.domain.data.dto.PopulationsTop10Response;
import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.dduckddak.domain.data.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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
        populations.sort(Comparator.comparing(o -> o.getTown().getQuarter()));
        return PopulationByQuarterDto.from(populations);
    }

    //최신 분기의 시간당 유동인구를 반환
    public TimelyDto getFloatingPopulationByCodeTop1(String code) {
        Population population = populationRepository.findTop1ByTownCodeAndPopulationTypeOrderByQuarterDesc(code, PopulationType.FloatingPopulation)
                .orElseThrow(() -> new IllegalArgumentException("해당 동의 유동인구 데이터가 존재하지 않습니다."));
        return TimelyDto.from(population);
    }


    public List<PopulationByDistrictResponse> getPopulationByDistrictTop5(String district, PopulationType populationType) {
        return populationRepository.findTop5ByDistrictAndPopulationTypeOrderByQuarterDesc(district, populationType);
    }

    public List<PopulationsTop10Response> getFloatingPopulationsTop10(String selectCriteria, String orderCriteria) {
        return populationRepository.findPopulationsTop10(selectCriteria, orderCriteria, "'floatingPopulation'");
    }

    public List<PopulationsTop10Response> getResidentPopulationsTop10(String gender, String age, String orderCriteria) {
        String columnName = "population";
        if(gender.equals("all"))
        {
            if(age.equals("all")) // 모든 인구 수 조회
                columnName = "total_" + columnName; // total_population
            else if(age.equals("60"))
                columnName = "age" + age + "s_AndMore" + columnName; // age60sAndMore_population
            else
                columnName = "age" + age + "s_" + columnName; // age10s_population(10,20,30,40,50)
        } else if(gender.equals("men")) {
            if(age.equals("all")) // 모든 남성 인구 수 조회
                columnName = "men_" + columnName; // men_population
            else if(age.equals("60"))
                columnName = "age" + age + "s_AndMore" + columnName + "_of_male"; // age60sAndMore_population_of_male
            else
                columnName = "age" + age + "s_" + columnName + "_of_male"; // age10s_population_of_male(10,20,30,40,50)
        } else if(gender.equals("women")) {
            if(age.equals("all")) // 모든 여성 인구 수 조회
                columnName = "women_" + columnName; // women_population
            else if(age.equals("60"))
                columnName = "age" + age + "s_AndMore" + columnName + "_of_female"; // age60sAndMore_population_of_male
            else
                columnName = "age" + age + "s_" + columnName + "_of_female"; // age10s_population_of_male(10,20,30,40,50)
        }
        System.out.println(columnName);
        return populationRepository.findPopulationsTop10(columnName, orderCriteria, "'residentPopulation'");
    }
}

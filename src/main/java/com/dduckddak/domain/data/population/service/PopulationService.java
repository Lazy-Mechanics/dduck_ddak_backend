package com.dduckddak.domain.data.population.service;

import com.dduckddak.domain.data.population.dto.*;
import com.dduckddak.domain.data.population.dto.PopulationTransitionResponse.PopulationData;
import com.dduckddak.domain.data.population.model.Population;
import com.dduckddak.domain.data.population.model.PopulationType;
import com.dduckddak.domain.data.population.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public PopulationTransitionResponse getFloatingPopulationTransition(String code) {
        List<Population> populations = populationRepository.findFloatingPopulationTransition(code);
        List<PopulationData> populationDataList = new ArrayList<>();

        int districtCount = 0;

        Map<Long , Long> map = new HashMap<>();
        long[] quarterArr = new long[]{20231L,  20232L, 20233L, 20234L ,20241L };
        for(long quarter : quarterArr){

            List<Population> listOfCity = populations.stream().filter
                    (p -> p.getTown().getQuarter().equals(quarter)).toList();

            Population population = listOfCity.stream().filter(p -> p.getTown().getCode().equals(code)).findFirst().get();
            long populationOfTown = population.getTotalPopulation();

            map.put(quarter, population.getTotalPopulation()); // 분기 별 매출 저장

            int rankAtCity = listOfCity.indexOf(population) + 1; // 20241분기 시 내 등수
            long populationAvgOfCity = (long) listOfCity.stream().mapToLong(Population::getTotalPopulation).average().getAsDouble();


            List<Population> listOfDistrict = listOfCity.stream().filter
                    (p -> p.getTown().getName().split(" ")[0].equals(population.getTown().getName().split(" ")[0])).toList();

            districtCount = listOfDistrict.size();

            int rankAtDistrict = listOfDistrict.indexOf(population) + 1; // 20241분기 구 내 등수
            long populationAvgOfDistrict = (long) listOfDistrict.stream().mapToLong(Population::getTotalPopulation).average().getAsDouble();

            populationDataList.add(new PopulationData(population.getTown().getName().split(" ")[1] ,quarter, populationOfTown, rankAtCity, populationAvgOfCity, rankAtDistrict, populationAvgOfDistrict));
        }
        long differenceFromPreviousQuarter = map.get(20241L) - map.get(20234L);
        long differenceFromPreviousYear  = map.get(20241L) - map.get(20231L);

        return PopulationTransitionResponse.from(populationDataList, districtCount, differenceFromPreviousQuarter, differenceFromPreviousYear);


    }
}

package com.dduckddak.domain.data.sales.service;

import com.dduckddak.domain.data.population.dto.TimelyDto;
import com.dduckddak.domain.data.sales.dto.*;
import com.dduckddak.domain.data.sales.repository.SalesRepository;
import com.dduckddak.domain.town.dto.SalesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SalesService {

    private final SalesRepository salesRepository;

    public TimelyDto getSalesByCode(String code) {
        return TimelyDto.from(salesRepository.findAllByTownCodeOrderByQuarterDesc(code));
    }

    public SalesResponse getSalesCompare(String code, String name) {
        return SalesResponse.of(salesRepository.getSalesCompare(code, name, 20241L, 20234L), false);
    }

    public List<SalesTop10Response> getSalesTop10(String orderCriteria) {
        return salesRepository.findSalesTop10(orderCriteria);
    }

    public List<SalesTop10OfIndustryResponse> getSalesTop10OfIndustry(String name, String orderCriteria) {
        return salesRepository.findSalesTop10OfIndustry(name, orderCriteria);
    }

    @Cacheable(value = "postsCache1", cacheManager = "redisCacheManager")
    public SalesTransitionResponse getSalesTransition(String code) {
        List<SalesForTransitionData> salesForTransitionData = salesRepository.findSalesForTransitionData();


        List<SalesTransitionResponse.SalesData> salesDataList = new ArrayList<>();
        int districtCount = 0;
        Map<Long , Long> map = new HashMap<>();
        long[] quarterArr = new long[]{20231L,  20232L, 20233L, 20234L ,20241L };
        for(long quarter : quarterArr){

            List<SalesForTransitionData> listOfCity = salesForTransitionData.stream().filter
                    (s -> s.getQuarter().equals(quarter)).toList();

            SalesForTransitionData sales = listOfCity.stream().filter(s -> s.getTownCode().equals(code)).findFirst().get();
            Long salesAtTown = sales.getSalesAtTown();
            map.put(quarter, sales.getSalesAtTown()); // 분기 별 매출 저장


            int rankAtCity = listOfCity.indexOf(sales) + 1; // 20241분기 시 내 등수
            long salesAvgOfCity = (long) listOfCity.stream().mapToLong(s -> s.getSalesAtTown()).average().getAsDouble();

            List<SalesForTransitionData> listOfDistrict = listOfCity.stream().filter
                    (s -> s.getTownName().split(" ")[0].equals(sales.getTownName().split(" ")[0])).toList();

            districtCount = listOfDistrict.size();

            int rankAtDistrict = listOfDistrict.indexOf(sales) + 1; // 20241분기 구 내 등수
            long populationAvgOfDistrict = (long) listOfDistrict.stream().mapToLong(s -> s.getSalesAtTown()).average().getAsDouble();

            salesDataList.add(new SalesTransitionResponse.SalesData(sales.getTownName().split(" ")[1] ,quarter, sales.getSalesAtTown(),  rankAtCity, salesAvgOfCity, rankAtDistrict, populationAvgOfDistrict));
        }
        long differenceFromPreviousQuarter = map.get(20241L) - map.get(20234L);
        long differenceFromPreviousYear  = map.get(20241L) - map.get(20231L);
        return SalesTransitionResponse.from(salesDataList, districtCount, differenceFromPreviousQuarter, differenceFromPreviousYear);
    }

    @Cacheable(value = "postsCache", cacheManager = "redisCacheManager")
    public SalesTransitionByIndustryResponse getSalesTransitionByIndustry(String townCode, String industryName) {
        List<SalesForTransitionData> salesForTransitionData = salesRepository.findSalesByIndustryForTransitionData(industryName);


        List<SalesTransitionByIndustryResponse.SalesData> salesDataList = new ArrayList<>();

        int districtCount = 0;

        Map<Long , Long> map = new HashMap<>();
        long[] quarterArr = new long[]{20231L,  20232L, 20233L, 20234L ,20241L };
        for(long quarter : quarterArr){

            List<SalesForTransitionData> listOfCity = salesForTransitionData.stream().filter
                    (s -> s.getQuarter().equals(quarter)).toList();

            SalesForTransitionData sales = listOfCity.stream().filter(s -> s.getTownCode().equals(townCode)).findFirst().get();
            Long salesAtTown = sales.getSalesAtTown();

            map.put(quarter, sales.getSalesAtTown()); // 분기 별 매출 저장

            int rankAtCity = listOfCity.indexOf(sales) + 1; // 20241분기 시 내 등수
            long salesAvgOfCity = (long) listOfCity.stream().mapToLong(s -> s.getSalesAtTown()).average().getAsDouble();


            List<SalesForTransitionData> listOfDistrict = listOfCity.stream().filter
                    (s -> s.getTownName().split(" ")[0].equals(sales.getTownName().split(" ")[0])).toList();

            districtCount = listOfDistrict.size();

            int rankAtDistrict = listOfDistrict.indexOf(sales) + 1; // 20241분기 구 내 등수
            long populationAvgOfDistrict = (long) listOfDistrict.stream().mapToLong(s -> s.getSalesAtTown()).average().getAsDouble();

            salesDataList.add(new SalesTransitionByIndustryResponse.SalesData(sales.getTownName().split(" ")[1], industryName, quarter, sales.getSalesAtTown(),  rankAtCity, salesAvgOfCity, rankAtDistrict, populationAvgOfDistrict));
        }
        long differenceFromPreviousQuarter = map.get(20241L) - map.get(20234L);
        long differenceFromPreviousYear  = map.get(20241L) - map.get(20231L);
        return SalesTransitionByIndustryResponse.from(salesDataList, districtCount, differenceFromPreviousQuarter, differenceFromPreviousYear);
    }

    public SalesRateByGenderAndIndustryResponse getSalesRateByGenderAndIndustry(String townCode, String industryName) {
        SalesRateByGenderAndIndustryResponse salesRateByGenderAndIndustry = salesRepository.findSalesRateByGenderAndIndustry(townCode, industryName);
        return salesRateByGenderAndIndustry;
    }

    public SalesRateByAgeAndIndustryResponse getSalesRateByAgeAndIndustry(String townCode, String industryName) {
        SalesRateByAgeAndIndustryResponse salesRateByAgeAndIndustryResponse = salesRepository.findSalesRateByAgeAndIndustry(townCode, industryName);
        return salesRateByAgeAndIndustryResponse;
    }
}

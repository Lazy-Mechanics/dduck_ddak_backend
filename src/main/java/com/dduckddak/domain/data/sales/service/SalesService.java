package com.dduckddak.domain.data.sales.service;

import com.dduckddak.domain.data.population.dto.TimelyDto;
import com.dduckddak.domain.data.sales.dto.*;
import com.dduckddak.domain.data.sales.repository.SalesRepository;
import com.dduckddak.domain.town.dto.SalesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public SalesTransitionResponse getSalesTransition(String code) {
        List<SalesForTransitionData> salesForTransitionData = salesRepository.findSalesForTransitionData();


        List<SalesTransitionResponse.SalesData> salesDataList = new ArrayList<>();

        long[] quarterArr = new long[]{20241L, 20234L, 20233L, 20232L, 20231L};
        for(long quarter : quarterArr){

            List<SalesForTransitionData> listOfCity = salesForTransitionData.stream().filter
                    (s -> s.getQuarter().equals(quarter)).toList();

            SalesForTransitionData sales = listOfCity.stream().filter(s -> s.getTownCode().equals(code)).findFirst().get();
            Long salesAtTown = sales.getSalesAtTown();

            int rankAtCity = listOfCity.indexOf(sales) + 1; // 20241분기 시 내 등수
            long salesAvgOfCity = (long) listOfCity.stream().mapToLong(s -> s.getSalesAtTown()).average().getAsDouble();


            List<SalesForTransitionData> listOfDistrict = listOfCity.stream().filter
                    (s -> s.getTownName().split(" ")[0].equals(sales.getTownName().split(" ")[0])).toList();

            int rankAtDistrict = listOfDistrict.indexOf(sales) + 1; // 20241분기 구 내 등수
            long populationAvgOfDistrict = (long) listOfDistrict.stream().mapToLong(s -> s.getSalesAtTown()).average().getAsDouble();

            salesDataList.add(new SalesTransitionResponse.SalesData(quarter, sales.getSalesAtTown(),  rankAtCity, salesAvgOfCity, rankAtDistrict, populationAvgOfDistrict));
        }

        return SalesTransitionResponse.from(salesDataList);
    }

    public SalesTransitionByIndustryResponse getSalesTransitionByIndustry(String townCode, String industryName) {
        List<SalesForTransitionData> salesForTransitionData = salesRepository.findSalesByIndustryForTransitionData(industryName);


        List<SalesTransitionByIndustryResponse.SalesData> salesDataList = new ArrayList<>();

        long[] quarterArr = new long[]{20241L, 20234L, 20233L, 20232L, 20231L};
        for(long quarter : quarterArr){

            List<SalesForTransitionData> listOfCity = salesForTransitionData.stream().filter
                    (s -> s.getQuarter().equals(quarter)).toList();

            SalesForTransitionData sales = listOfCity.stream().filter(s -> s.getTownCode().equals(townCode)).findFirst().get();
            Long salesAtTown = sales.getSalesAtTown();

            int rankAtCity = listOfCity.indexOf(sales) + 1; // 20241분기 시 내 등수
            long salesAvgOfCity = (long) listOfCity.stream().mapToLong(s -> s.getSalesAtTown()).average().getAsDouble();


            List<SalesForTransitionData> listOfDistrict = listOfCity.stream().filter
                    (s -> s.getTownName().split(" ")[0].equals(sales.getTownName().split(" ")[0])).toList();

            int rankAtDistrict = listOfDistrict.indexOf(sales) + 1; // 20241분기 구 내 등수
            long populationAvgOfDistrict = (long) listOfDistrict.stream().mapToLong(s -> s.getSalesAtTown()).average().getAsDouble();

            salesDataList.add(new SalesTransitionByIndustryResponse.SalesData(quarter, sales.getSalesAtTown(),  rankAtCity, salesAvgOfCity, rankAtDistrict, populationAvgOfDistrict));
        }

        return SalesTransitionByIndustryResponse.from(salesDataList);
    }
}

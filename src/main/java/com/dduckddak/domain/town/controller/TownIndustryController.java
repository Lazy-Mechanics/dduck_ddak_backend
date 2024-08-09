package com.dduckddak.domain.town.controller;

import com.dduckddak.domain.data.dto.MarketTrendsResponse;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
import com.dduckddak.domain.town.dto.SalesResponse;
import com.dduckddak.domain.town.service.TownIndustryService;
import com.dduckddak.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dduckddak.global.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/towns/industry")
@Tag(name = "행정동업종 정보 요청", description = "행정동업종 API")
public class TownIndustryController {
    private final TownIndustryService townIndustryService;

    @Operation(summary = "행정동단위 현재 업종수 조회", description = "parameter로 행정동코드(code)와 업종명(name)을 받아 해당 구의 현재 업종수를 조회")
    @GetMapping("/recently")
    public ApiResponse<List<RecentlyTownIndustryResponse>> getRecentlyIndustries(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getRecentlyIndustries(code, name));
    }

    @Operation(summary = "구단위 현재 업종수 조회", description = "parameter로 구(district)와 업종명(name)을 받아 해당 구의 현재 업종수를 조회, '비교 차트로 사용'")
    @GetMapping("/recently-district")
    public ApiResponse<List<RecentlyTownIndustryResponse>> getRecentlyIndustriesInDistrict(
            @RequestParam(value = "district") String district,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getRecentlyIndustriesInDistrict(district, name));
    }

    @Operation(summary = "행정동단위 유사 업종수 조회", description = "parameter로 행정동코드(code)와 업종명(name)을 받아 해당 구의 유사 업종수를 조회. 표시되는 데이터는 ")
    @GetMapping("/similar")
    public ApiResponse<List<SimilarTownIndustryDto>> getSimilarIndustries(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getSimilarIndustries(code, name));
    }

    @Operation(summary = "구단위 유사 업종수 조회", description = "parameter로 구(district)와 업종명(name)을 받아 해당 구의 유사 업종수를 조회")
    @GetMapping("/similar-district")
    public ApiResponse<List<SimilarTownIndustryDto>> getSimilarIndustriesInDistrict(
            @RequestParam(value = "district") String district,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getSimilarIndustriesInDistrict(district, name));
    }

    @Operation(summary = "행정동단위 업종 평균 영업 기간", description = "parameter로 행정동코드(code)와 분기(quarter)를 받아 해당 구의 업종 평균 영업 기간을 조회")
    @GetMapping("/business-period")
    public ApiResponse<MarketTrendsResponse> getIndustriesBusinessPeriod(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "quarter") int quarter) {
        return success(townIndustryService.getIndustriesBusinessPeriod(code, quarter));
    }

    @Operation(summary = "구단위 업종 평균 영업 기간", description = "parameter로 구(district)와 분기(quarter)를 받아 해당 구의 업종 평균 영업 기간을 조회")
    @GetMapping("/business-period-district")
    public ApiResponse<MarketTrendsResponse> getIndustriesBusinessPeriodInDistrict(
            @RequestParam(value = "district") String district,
            @RequestParam(value = "quarter") int quarter) {
        return success(townIndustryService.getIndustriesBusinessPeriodInDistrict(district, quarter));
    }

    @Operation(summary = "행정동단위 업종 요일별 매출 조회 ", description = "parameter로 행정동코드(code)와 업종명(name)을 받아 해당 구의 업종 요일별 매출을 조회 '비교 차트로 사용'")
    @GetMapping("/sales")
    public ApiResponse<SalesResponse> getIndustriesSales(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getIndustriesSales(code, name));
    }

    @Operation(summary = "구단위 업종 요일별 매출 조회", description = "parameter로 구(district)와 업종명(name)을 받아 해당 구의 업종 요일별 매출을 조회")
    @GetMapping("/sales-district")
    public ApiResponse<SalesResponse> getIndustriesSalesInDistrict(
            @RequestParam(value = "district") String district,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getIndustriesSalesInDistrict(district, name));
    }
}

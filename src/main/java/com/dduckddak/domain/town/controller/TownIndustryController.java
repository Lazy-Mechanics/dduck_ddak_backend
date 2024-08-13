package com.dduckddak.domain.town.controller;

import com.dduckddak.domain.data.dto.*;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.SalesResponse;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
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
@Tag(name = "행정동업종 정보 요청", description = "행정동업종 API")
public class TownIndustryController {
    private final TownIndustryService townIndustryService;

    @Operation(summary = "행정동단위 현재 업종수 조회", description = "parameter로 행정동코드(code)와 업종명(name)을 받아 해당 구의 현재 업종수를 조회")
    @GetMapping("/towns/industry/recently")
    public ApiResponse<List<RecentlyTownIndustryResponse>> getRecentlyIndustries(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getRecentlyIndustries(code, name));
    }

    @Operation(summary = "구단위 현재 업종수 조회", description = "parameter로 구(district)와 업종명(name)을 받아 해당 구의 현재 업종수를 조회, '비교 차트로 사용'")
    @GetMapping("/towns/industry/recently-district")
    public ApiResponse<List<RecentlyTownIndustryResponse>> getRecentlyIndustriesInDistrict(
            @RequestParam(value = "district") String district,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getRecentlyIndustriesInDistrict(district, name));
    }

    @Operation(summary = "행정동단위 유사 업종수 조회", description = "parameter로 행정동코드(code)와 업종명(name)을 받아 해당 구의 유사 업종수를 조회. 표시되는 데이터는 ")
    @GetMapping("/towns/industry/similar")
    public ApiResponse<List<SimilarTownIndustryDto>> getSimilarIndustries(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getSimilarIndustries(code, name));
    }

    @Operation(summary = "구단위 유사 업종수 조회", description = "parameter로 구(district)와 업종명(name)을 받아 해당 구의 유사 업종수를 조회")
    @GetMapping("/towns/industry/similar-district")
    public ApiResponse<List<SimilarTownIndustryDto>> getSimilarIndustriesInDistrict(
            @RequestParam(value = "district") String district,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getSimilarIndustriesInDistrict(district, name));
    }

    @Operation(summary = "행정동단위 업종 평균 영업 기간", description = "parameter로 행정동코드(code)와 분기(quarter)를 받아 해당 구의 업종 평균 영업 기간을 조회")
    @GetMapping("/towns/industry/business-period")
    public ApiResponse<MarketTrendsResponse> getIndustriesBusinessPeriod(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "quarter") int quarter) {
        return success(townIndustryService.getIndustriesBusinessPeriod(code, quarter));
    }

    @Operation(summary = "구단위 업종 평균 영업 기간", description = "parameter로 구(district)와 분기(quarter)를 받아 해당 구의 업종 평균 영업 기간을 조회")
    @GetMapping("/towns/industry/business-period-district")
    public ApiResponse<MarketTrendsResponse> getIndustriesBusinessPeriodInDistrict(
            @RequestParam(value = "district") String district,
            @RequestParam(value = "quarter") int quarter) {
        return success(townIndustryService.getIndustriesBusinessPeriodInDistrict(district, quarter));
    }

    @Operation(summary = "행정동단위 업종 요일별 매출 조회 ", description = "parameter로 행정동코드(code)와 업종명(name)을 받아 해당 구의 업종 요일별 매출을 조회 '비교 차트로 사용'")
    @GetMapping("/towns/industry/sales")
    public ApiResponse<SalesResponse> getIndustriesSales(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getIndustriesSales(code, name));
    }

    @Operation(summary = "구단위 업종 요일별 매출 조회", description = "parameter로 구(district)와 업종명(name)을 받아 해당 구의 업종 요일별 매출을 조회")
    @GetMapping("/towns/industry/sales-district")
    public ApiResponse<SalesResponse> getIndustriesSalesInDistrict(
            @RequestParam(value = "district") String district,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getIndustriesSalesInDistrict(district, name));
    }

    @Operation(summary = "스크랩 보고서 메일 전송", description = "parameter로 행정동코드(code)와 업종명(name)을 받아 특정 행정동의 해당 분기 업종 정보를 메일로 전송")
    @GetMapping("/towns/industry/sales-info")
    public ApiResponse<MarketAnalysisResponse> getTownSalesInfo(@RequestParam(value = "code") Long code, @RequestParam(value = "name") String name
                                                                ) { // @AuthenticationPrincipal String email
        return success(townIndustryService.getTownSalesInfo(code, name, "hyeri0603@naver.com"));
    }

    @Operation(summary = "행정동 별 업종 수 Top10", description = "좌측 하단 업종 수 top10 UI에 사용(increaseRate or storeCount20241)")
    @GetMapping("/towns/store-count/top10")
    public ApiResponse<List<StoreCountTop10Response>> getStoreCountTop10(@RequestParam(value = "orderCriteria", defaultValue = "storeCount20241") String orderCriteria) {
        return ApiResponse.success(townIndustryService.getStoreCountTop10(orderCriteria));
    }


    @Operation(summary = "행정동 별 업종 별 업종 수 Top10", description = "좌측 하단 업종 수 top10 UI에 사용(선택한 업종에 대해 행정동 별 업종 수 TOP10) (increaseRate or storeCount20241)")
    @GetMapping("/towns/industry/store-count/top10")
    public ApiResponse<List<StoreCountTop10OfIndustryResponse>> getStoreCountTop10OfIndustry(@RequestParam(value = "orderCriteria", defaultValue = "storeCount20241") String orderCriteria, @RequestParam(value = "industryName") String industryName) {
        return ApiResponse.success(townIndustryService.getSalesTop10OfIndustry(industryName, orderCriteria));

    }


}

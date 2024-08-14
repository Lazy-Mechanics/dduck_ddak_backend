package com.dduckddak.domain.data.sales.controller;

import com.dduckddak.domain.data.population.dto.TimelyDto;
import com.dduckddak.domain.data.sales.dto.SalesTop10OfIndustryResponse;
import com.dduckddak.domain.data.sales.dto.SalesTop10Response;
import com.dduckddak.domain.data.sales.dto.SalesTransitionResponse;
import com.dduckddak.domain.data.sales.service.SalesService;
import com.dduckddak.domain.town.dto.SalesResponse;
import com.dduckddak.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dduckddak.global.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@Tag(name = "매출 추이 요청", description = "매출 API")
public class SalesController {

    private final SalesService salesService;

    @Operation(summary = "행정동단위 전체 업종 시간대별 매출 조회", description = "parameter로 행정동코드(code)를 받아 해당 행정동의 전체 업종 시간대별 매출을 조회 '비교 차트로 사용'")
    @GetMapping("/towns/sales/time")
    public ApiResponse<TimelyDto> getSalesByCode(String code) {
        return ApiResponse.success(salesService.getSalesByCode(code));
    }

    // 전분기 매출 비교
    @Operation(summary = "이거는 지금 예비 기능임 무시 ㄱㄱㄱㄱㄱ", description = "이거는 지금 예비 기능임 무시 ㄱㄱㄱㄱㄱ")
    @GetMapping("/towns/sales/compare")
    public ApiResponse<SalesResponse> getSalesCompare(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "name") String name) {
        return ApiResponse.success(salesService.getSalesCompare(code, name));
    }

    @Operation(summary = "행정동 별 매출 Top10", description = "좌측 하단 매출 top10 UI에 사용(increaseRate or sales20241)")
    @GetMapping("/towns/sales/top10")
    public ApiResponse<List<SalesTop10Response>> getSalesTop10(@RequestParam(value = "orderCriteria", defaultValue = "sales20241") String orderCriteria) {
        return ApiResponse.success(salesService.getSalesTop10(orderCriteria));
    }

    @Operation(summary = "행정동 별 업종 별 매출 Top10", description = "좌측 하단 매출 top10 UI에 사용(선택한 업종에 대해 행정동 별 매출 TOP10) (increaseRate or sales20241)")
    @GetMapping("/towns/industries/sales/top10")
    public ApiResponse<List<SalesTop10OfIndustryResponse>> getSalesTop10OfIndustry(@RequestParam(value = "orderCriteria", defaultValue = "sales20241") String orderCriteria, @RequestParam(value = "industryName") String name) {
        return ApiResponse.success(salesService.getSalesTop10OfIndustry(name, orderCriteria));
    }

    @Operation(summary = "행정동의 매출 추이")
    @GetMapping("/towns/sales/transition")
    public ApiResponse<SalesTransitionResponse> getSalesTransition(@RequestParam(value = "code") String code){
        SalesTransitionResponse salesTransitionResponse = salesService.getSalesTransition(code);
        return success(salesTransitionResponse);
    }
}

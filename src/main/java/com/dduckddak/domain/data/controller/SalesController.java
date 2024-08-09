package com.dduckddak.domain.data.controller;

import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.service.SalesService;
import com.dduckddak.domain.town.dto.SalesResponse;
import com.dduckddak.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/towns/sales")
@Tag(name = "매출 추이 요청", description = "매출 API")
public class SalesController {

    private final SalesService salesService;

    @Operation(summary = "행정동단위 전체 업종 시간대별 매출 조회", description = "parameter로 행정동코드(code)를 받아 해당 행정동의 전체 업종 시간대별 매출을 조회 '비교 차트로 사용'")
    @GetMapping("/time")
    public ApiResponse<TimelyDto> getSalesByCode(String code) {
        return ApiResponse.success(salesService.getSalesByCode(code));
    }

    // 전분기 매출 비교
    @Operation(summary = "이거는 지금 예비 기능임 무시 ㄱㄱㄱㄱㄱ", description = "이거는 지금 예비 기능임 무시 ㄱㄱㄱㄱㄱ")
    @GetMapping("/compare")
    public ApiResponse<SalesResponse> getSalesCompare(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "name") String name) {
        return ApiResponse.success(salesService.getSalesCompare(code, name));
    }
}

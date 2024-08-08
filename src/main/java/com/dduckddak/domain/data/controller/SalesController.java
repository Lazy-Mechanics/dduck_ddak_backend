package com.dduckddak.domain.data.controller;

import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.service.SalesService;
import com.dduckddak.domain.town.dto.SalesResponse;
import com.dduckddak.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/towns/sales")
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/time")
    public ApiResponse<TimelyDto> getSalesByCode(String code) {
        return ApiResponse.success(salesService.getSalesByCode(code));
    }

    // 전분기 매출 비교
    @GetMapping("/compare")
    public ApiResponse<SalesResponse> getSalesCompare(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "name") String name) {
        return ApiResponse.success(salesService.getSalesCompare(code, name));
    }
}

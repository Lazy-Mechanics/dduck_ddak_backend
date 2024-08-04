package com.dduckddak.domain.data.controller;

import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.service.SalesService;
import com.dduckddak.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}

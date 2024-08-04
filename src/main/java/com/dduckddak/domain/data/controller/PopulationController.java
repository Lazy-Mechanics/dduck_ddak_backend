package com.dduckddak.domain.data.controller;

import com.dduckddak.domain.data.dto.FloatingPopulationByQuarterDto;
import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.service.PopulationService;
import com.dduckddak.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dduckddak.global.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/towns/populations")
public class PopulationController {

    private final PopulationService populationService;

    @GetMapping("/floating/quarter")
    public ApiResponse<FloatingPopulationByQuarterDto> getFloatingPopulationByCodeTop5(String code) {

        return success(populationService.getFloatingPopulationByCodeTop5(code));
    }

    @GetMapping("/floating/time")
    public ApiResponse<TimelyDto> getFloatingPopulationByCodeTop1(String code) {

        return success(populationService.getFloatingPopulationByCodeTop1(code));
    }
}

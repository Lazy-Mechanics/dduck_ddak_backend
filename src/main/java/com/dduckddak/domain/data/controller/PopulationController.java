package com.dduckddak.domain.data.controller;

import com.dduckddak.domain.data.dto.PopulationByQuarterDto;
import com.dduckddak.domain.data.dto.TimelyDto;
import com.dduckddak.domain.data.model.PopulationType;
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
    public ApiResponse<PopulationByQuarterDto> getFloatingPopulationByCodeTop5(String code) {
        return success(populationService.getPopulationByCodeTop5(code, PopulationType.FloatingPopulation));
    }
    @GetMapping("/resident/quarter")
    public ApiResponse<PopulationByQuarterDto> getResidentPopulationByCodeTop5(String code) {
        return success(populationService.getPopulationByCodeTop5(code, PopulationType.ResidentPopulation));
    }
    @GetMapping("/working/quarter")
    public ApiResponse<PopulationByQuarterDto> getWorkingPopulationByCodeTop5(String code) {
        return success(populationService.getPopulationByCodeTop5(code, PopulationType.WorkingPopulation));
    }

    @GetMapping("/floating/time")
    public ApiResponse<TimelyDto> getFloatingPopulationByCodeTop1(String code) {

        return success(populationService.getFloatingPopulationByCodeTop1(code));
    }
}

package com.dduckddak.domain.town.controller;

import com.dduckddak.domain.data.dto.MarketTrendsResponse;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryDto;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
import com.dduckddak.domain.town.service.TownIndustryService;
import com.dduckddak.global.ApiResponse;
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
public class TownIndustryController {
    private final TownIndustryService townIndustryService;

    @GetMapping("/recently")
    public ApiResponse<List<RecentlyTownIndustryDto>> getRecentlyIndustries(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getRecentlyIndustries(code, name));
    }

    @GetMapping("/similar")
    public ApiResponse<List<SimilarTownIndustryDto>> getSimilarIndustries(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getSimilarIndustries(code, name));
    }

    @GetMapping("/business-period")
    public ApiResponse<MarketTrendsResponse> getIndustriesBusinessPeriod(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "quarter") int quarter) {
        return success(townIndustryService.getIndustriesBusinessPeriod(code, quarter));
    }

    @GetMapping("/sales")
    public ApiResponse<SalesResponse> getIndustriesSales(
            @RequestParam(value = "code") int code,
            @RequestParam(value = "name") String name) {
        return success(townIndustryService.getIndustriesSales(code, name));
    }

}

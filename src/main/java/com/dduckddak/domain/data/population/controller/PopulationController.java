package com.dduckddak.domain.data.population.controller;

import com.dduckddak.domain.data.population.dto.*;
import com.dduckddak.domain.data.population.model.PopulationType;
import com.dduckddak.domain.data.population.service.PopulationService;
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
@RequestMapping("/towns/populations")
@Tag(name = "인구 추이 요청", description = "인구 API")
public class PopulationController {

    private final PopulationService populationService;

    @Operation(summary = "행정동의 유동인구 수 추이")
    @GetMapping("/floating/transition")
    public ApiResponse<PopulationTransitionResponse> getFloatingPopulationTransition(@RequestParam(value = "code") String code){
        PopulationTransitionResponse floatingPopulationTransition = populationService.getFloatingPopulationTransition(code);
        return success(floatingPopulationTransition);
    }

    @Operation(summary = "행정동별 전체 유동인구 조회", description = "parameter로 행정동코드(code)를 받아 해당 행정동의 전체 유동인구를 조회")
    @GetMapping("/floating/quarter")
    public ApiResponse<PopulationByQuarterDto> getFloatingPopulationByCodeTop5(@RequestParam(value = "code") String code) {
        return success(populationService.getPopulationByCodeTop5(code, PopulationType.FloatingPopulation));
    }

    @Operation(summary = "행정동별 전체 상주인구 조회", description = "parameter로 행정동코드(code)를 받아 해당 행정동의 전체 주민인구를 조회")
    @GetMapping("/resident/quarter")
    public ApiResponse<PopulationByQuarterDto> getResidentPopulationByCodeTop5(@RequestParam(value = "code") String code) {
        return success(populationService.getPopulationByCodeTop5(code, PopulationType.ResidentPopulation));
    }

    @Operation(summary = "행정동별 전체 직장인구 조회", description = "parameter로 행정동코드(code)를 받아 해당 행정동의 전체 직장인구를 조회")
    @GetMapping("/working/quarter")
    public ApiResponse<PopulationByQuarterDto> getWorkingPopulationByCodeTop5(@RequestParam(value = "code") String code) {
        return success(populationService.getPopulationByCodeTop5(code, PopulationType.WorkingPopulation));
    }

    @Operation(summary = "구별 유동인구 조회", description = "parameter로 구(district)를 받아 해당 구의 유동인구를 조회")
    @GetMapping("district/floating/quarter")
    public ApiResponse<List<PopulationByDistrictResponse>> getFloatingPopulationByDistrictTop5(@RequestParam(value = "district") String district) {
        return success(populationService.getPopulationByDistrictTop5(district, PopulationType.FloatingPopulation));
    }

    @Operation(summary = "구별 상주인구 조회", description = "parameter로 구(district)를 받아 해당 구의 상주인구를 조회")
    @GetMapping("district/resident/quarter")
    public ApiResponse<List<PopulationByDistrictResponse>> getResidentPopulationByDistrictTop5(@RequestParam(value = "district") String district) {
        return success(populationService.getPopulationByDistrictTop5(district, PopulationType.ResidentPopulation));
    }

    @Operation(summary = "구별 직장인구 조회", description = "parameter로 구(district)를 받아 해당 구의 직장인구를 조회")
    @GetMapping("district/working/quarter")
    public ApiResponse<List<PopulationByDistrictResponse>> getWorkingPopulationByDistrictTop5(@RequestParam(value = "district") String district) {
        return success(populationService.getPopulationByDistrictTop5(district, PopulationType.WorkingPopulation));
    }

    @Operation(summary = "행정동별 유동인구 조회", description = "parameter로 행정동코드(code)를 받아 해당 행정동의 유동인구를 조회")
    @GetMapping("/floating/time")
    public ApiResponse<TimelyDto> getFloatingPopulationByCodeTop1(String code) {

        return success(populationService.getFloatingPopulationByCodeTop1(code));
    }

    @Operation(summary = "행정동 별 유동인구 Top10", description = "좌측 하단 유동인구 top10 UI에 사용(조회 조건은 컬럼명, 정렬 조건은 populations or increaseRate)")
    @GetMapping("floating/top10")
    public ApiResponse<List<PopulationsTop10Response>> getFloatingPopulationsTop10(@RequestParam(value = "orderCriteria") String orderCriteria
            , @RequestParam(value = "selectCriteria", defaultValue = "total_population") String selectCriteria) {
        return ApiResponse.success(populationService.getFloatingPopulationsTop10(selectCriteria, orderCriteria));
    }

    @Operation(summary = "행정동 별 주거인구 Top10", description = "좌측 하단 유동인구 top10 UI에 사용(조회 조건은 컬럼명, 정렬 조건은 populations or increaseRate)")
    @GetMapping("resident/top10")
    public ApiResponse<List<PopulationsTop10Response>> getResidentPopulationsTop10(@RequestParam(value = "orderCriteria") String orderCriteria
            , @RequestParam(value = "gender", defaultValue = "all") String gender
            , @RequestParam(value = "age", defaultValue = "all") String age) {
        return ApiResponse.success(populationService.getResidentPopulationsTop10(gender, age, orderCriteria));
    }
}

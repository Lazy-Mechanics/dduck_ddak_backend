package com.dduckddak.domain.data.facility.controller;


import com.dduckddak.domain.data.facility.dto.FacilityByDistrictResponse;
import com.dduckddak.domain.data.facility.dto.FacilityDto;
import com.dduckddak.domain.data.facility.service.FacilityService;
import com.dduckddak.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.dduckddak.global.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/towns/facility")
@Tag(name = "주객시설 종류 수 요청", description = "주객시설 API")
public class FacilityController {

    private final FacilityService facilityService;

    @Operation(summary = "행정동단위 주객시설 종류 수 조회", description = "parameter로 시설 코드(code)를 받아 해당 행정동의 주객시설 종류 수를 조회")
    @GetMapping
    public ApiResponse<FacilityDto> getFacility(@RequestParam(value = "code") String code) {

        return success(facilityService.getFacility(code));
    }

    @Operation(summary = "구별 주객시설 종류 수 조회", description = "parameter로 구(district)를 받아 해당 구의 주객시설 종류 수를 조회")
    @GetMapping("/district")
    public ApiResponse<FacilityByDistrictResponse> getFacilityByDistrict(@RequestParam(value = "name") String name) {

        return success(facilityService.getFacilityByDistrict(name));
    }
}

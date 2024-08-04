package com.dduckddak.domain.data.controller;


import com.dduckddak.domain.data.dto.FacilityDto;
import com.dduckddak.domain.data.dto.FloatingPopulationByQuarterDto;
import com.dduckddak.domain.data.service.FacilityService;
import com.dduckddak.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dduckddak.global.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/towns/facility")
public class FacilityController {

    private FacilityService facilityService;

    @GetMapping()
    public ApiResponse<FacilityDto> getFacility(String code) {

        return success(facilityService.getfacility(code));
    }
}

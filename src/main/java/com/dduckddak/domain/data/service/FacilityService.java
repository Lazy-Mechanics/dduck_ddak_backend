package com.dduckddak.domain.data.service;

import com.dduckddak.domain.data.dto.FacilityByDistrictResponse;
import com.dduckddak.domain.data.dto.FacilityDto;
import com.dduckddak.domain.data.model.Facility;
import com.dduckddak.domain.data.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityDto getFacility(String code) {
        Facility facility = facilityRepository.findByTownCodeAndQuarter(code);
        return FacilityDto.of(facility);
    }

    public FacilityByDistrictResponse getFacilityByDistrict(String name) {
        return facilityRepository.findRecentByDistrict(name);
    }
}

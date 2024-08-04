package com.dduckddak.domain.data.service;

import com.dduckddak.domain.data.dto.FacilityDto;
import com.dduckddak.domain.data.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private FacilityRepository facilityRepository;

    public FacilityDto getfacility(String code) {
        facilityRepository.findAll();
        return null;
    }
}

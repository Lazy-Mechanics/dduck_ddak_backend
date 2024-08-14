package com.dduckddak.domain.data.facility.repository;

import com.dduckddak.domain.data.facility.dto.FacilityByDistrictResponse;
import com.dduckddak.domain.data.facility.model.Facility;

public interface FacilityRepositoryCustom {

    Facility findByTownCodeAndQuarter(String code);

    FacilityByDistrictResponse findRecentByDistrict(String district);
}

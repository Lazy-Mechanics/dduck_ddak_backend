package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.dto.FacilityByDistrictResponse;
import com.dduckddak.domain.data.model.Facility;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;

import java.util.List;

public interface FacilityRepositoryCustom {

    Facility findByTownCodeAndQuarter(String code);

    FacilityByDistrictResponse findRecentByDistrict(String district);
}

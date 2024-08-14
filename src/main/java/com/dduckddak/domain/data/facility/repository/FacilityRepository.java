package com.dduckddak.domain.data.facility.repository;

import com.dduckddak.domain.data.facility.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long>, FacilityRepositoryCustom {
}

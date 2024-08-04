package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Facility;
import com.dduckddak.domain.data.model.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long>, FacilityRepositoryCustom {
}

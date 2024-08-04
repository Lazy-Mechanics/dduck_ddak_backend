package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Population;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopulationRepository extends JpaRepository<Population, Long>, PopulationRepositoryCustom {

}

package com.dduckddak.domain.data.population.repository;

import com.dduckddak.domain.data.population.model.Population;
import com.dduckddak.domain.data.population.model.PopulationType;
import com.dduckddak.domain.town.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopulationRepository extends JpaRepository<Population, Long>, PopulationRepositoryCustom {

    Population findByTownAndPopulationType(Town town, PopulationType populationType);





}

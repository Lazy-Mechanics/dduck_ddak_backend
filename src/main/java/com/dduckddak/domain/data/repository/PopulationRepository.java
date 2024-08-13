package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.dto.PopulationsTop10Response;
import com.dduckddak.domain.data.dto.SalesTop10Response;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.dduckddak.domain.town.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PopulationRepository extends JpaRepository<Population, Long>, PopulationRepositoryCustom {

    Population findByTownAndPopulationType(Town town, PopulationType populationType);





}

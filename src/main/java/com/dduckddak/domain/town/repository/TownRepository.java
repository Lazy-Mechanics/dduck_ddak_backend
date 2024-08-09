package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TownRepository extends JpaRepository<Town, Long> {

    Optional<Town> findByNameAndQuarter(String name, Long quarter);
    Optional<Town> findByCode(String code);
    Optional<Town> findByCodeAndQuarter(String code, Long quarter);
}

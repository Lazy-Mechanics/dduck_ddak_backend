package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TownRepository extends JpaRepository<Town, Long> {

    Optional<Town> findByNameAndQuarter(String name, Long quarter);
    Optional<Town> findByCode(String code);

    @Query(value = "SELECT * FROM town WHERE code = :code AND quarter = :quarter", nativeQuery = true)
    Optional<Town> findByCodeAndQuarter(@Param("code") String code, @Param("quarter") Long quarter);
}

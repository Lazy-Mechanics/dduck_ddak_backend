package com.dduckddak.domain.member.repository;

import com.dduckddak.domain.member.model.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByTownCode(Long code);
    @Query("select case when count(s) > 0 then true else false end from Scrap s where s.townCode = :townCode and s.quarter = :quarter")
    boolean existsByTownCodeAndQuarter(@Param("townCode") Long townCode, @Param("quarter") Long quarter);
}

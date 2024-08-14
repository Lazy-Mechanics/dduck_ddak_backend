package com.dduckddak.domain.data.member.repository;

import com.dduckddak.domain.data.member.model.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByTownCode(Long code);
    boolean existsByTownCodeAndQuarter(Long townCode, Long quarter);
}

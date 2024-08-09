package com.dduckddak.domain.member.repository;

import com.dduckddak.domain.member.model.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByTownCode(Long code);
    boolean existsByTownCodeAndIndustryNameAndQuarter(Long code, String industryName, Long quarter);
}

package com.dduckddak.domain.town.repository;

import com.dduckddak.domain.town.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Integer> {
    Optional<Industry> findByCode(String code);
}

package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
}

package com.dduckddak.domain.data.repository;

import com.dduckddak.domain.data.model.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
}

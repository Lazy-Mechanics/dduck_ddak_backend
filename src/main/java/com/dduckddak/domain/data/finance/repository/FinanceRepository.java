package com.dduckddak.domain.data.finance.repository;

import com.dduckddak.domain.data.finance.model.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
}

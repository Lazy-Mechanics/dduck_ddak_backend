package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.model.Sales;

import java.util.Optional;

public interface SalesRepositoryCustom {
    Optional<Sales> findTop1ByTownCodeOrderByQuarterDesc(String code);
    Sales findByTownAndIndustry(int code, String name);

}

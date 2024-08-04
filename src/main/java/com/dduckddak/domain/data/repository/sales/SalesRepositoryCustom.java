package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.dto.RecentlySalesDto;
import com.dduckddak.domain.data.model.Sales;

public interface SalesRepositoryCustom {

    RecentlySalesDto findAllByTownCodeOrderByQuarterDesc(String code);
    Sales findByTownAndIndustry(int code, String name);

}

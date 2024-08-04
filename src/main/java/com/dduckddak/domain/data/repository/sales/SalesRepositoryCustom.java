package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.dto.RecentlySalesDto;
import com.dduckddak.domain.data.model.Sales;

import java.util.List;

public interface SalesRepositoryCustom {

    RecentlySalesDto findAllByTownCodeOrderByQuarterDesc(String code);
    Sales findByTownAndIndustry(int code, String name);
    List<Sales> findByTownAndIndustryInDistrict(String district, String name);
}

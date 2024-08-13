package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.dto.RecentlySalesDto;
import com.dduckddak.domain.data.dto.SalesForTransitionData;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.town.dto.SalesVO;

import java.util.List;

public interface SalesRepositoryCustom {

    RecentlySalesDto findAllByTownCodeOrderByQuarterDesc(String code);
    SalesVO findByTownAndIndustry(int code, String name);
    List<Sales> findByTownAndIndustryInDistrict(String district, String name);

    List<SalesForTransitionData> findSalesForTransitionData();
}

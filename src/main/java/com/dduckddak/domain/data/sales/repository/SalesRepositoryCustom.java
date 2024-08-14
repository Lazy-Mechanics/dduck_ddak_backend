package com.dduckddak.domain.data.sales.repository;

import com.dduckddak.domain.data.sales.dto.RecentlySalesDto;
import com.dduckddak.domain.data.sales.dto.SalesForTransitionData;
import com.dduckddak.domain.data.sales.dto.SalesTop10OfIndustryResponse;
import com.dduckddak.domain.data.sales.dto.SalesTop10Response;
import com.dduckddak.domain.data.sales.model.Sales;
import com.dduckddak.domain.town.dto.SalesVO;

import java.util.List;

public interface SalesRepositoryCustom {

    RecentlySalesDto findAllByTownCodeOrderByQuarterDesc(String code);
    SalesVO findByTownAndIndustry(int code, String name);
    List<Sales> findByTownAndIndustryInDistrict(String district, String name);




    List<SalesTop10Response> findSalesTop10(String orderCriteria);

    List<SalesTop10OfIndustryResponse> findSalesTop10OfIndustry(String name, String orderCriteria);

    List<SalesForTransitionData> findSalesForTransitionData();

    List<SalesForTransitionData> findSalesByIndustryForTransitionData(String industryName);
}

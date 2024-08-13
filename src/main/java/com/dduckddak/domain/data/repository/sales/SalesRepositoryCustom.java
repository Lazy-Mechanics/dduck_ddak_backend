package com.dduckddak.domain.data.repository.sales;

import com.dduckddak.domain.data.dto.RecentlySalesDto;
import com.dduckddak.domain.data.dto.SalesForTransitionData;
import com.dduckddak.domain.data.dto.SalesTop10OfIndustryResponse;
import com.dduckddak.domain.data.dto.SalesTop10Response;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.town.dto.SalesVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesRepositoryCustom {

    RecentlySalesDto findAllByTownCodeOrderByQuarterDesc(String code);
    SalesVO findByTownAndIndustry(int code, String name);
    List<Sales> findByTownAndIndustryInDistrict(String district, String name);

    List<SalesForTransitionData> findSalesForTransitionData();


    List<SalesTop10Response> findSalesTop10(String orderCriteria);

    List<SalesTop10OfIndustryResponse> findSalesTop10OfIndustry(String name, String orderCriteria);
}

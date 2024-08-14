package com.dduckddak.domain.data.sales.dto;

public interface SalesRateByGenderAndIndustryResponse {
    String getTownName();
    String getIndustryName();
    Double getMenPercentage();
    Double getWomenPercentage();
    Long getSalesOfIndustry();
}

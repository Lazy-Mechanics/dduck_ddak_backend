package com.dduckddak.domain.data.dto;


public interface SalesTop10OfIndustryResponse {
    String getTownName();
    String getIndustryName();
    Long getQuarter();
    Long getSales20234();
    Long getSales20241();
    Long getSalesDifference();
    float getIncreaseRate();
}

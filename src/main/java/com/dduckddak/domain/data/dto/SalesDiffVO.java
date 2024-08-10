package com.dduckddak.domain.data.dto;

public interface SalesDiffVO {
    String getTownName();
    String getQuarter();
    String getIndustryName();
    Long getCurrentQuarterSales();
    Double getIncreaseRate();
}

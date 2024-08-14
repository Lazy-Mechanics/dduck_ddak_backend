package com.dduckddak.domain.data.sales.dto;

public interface SalesDiffVO {
    String getTownName();
    String getQuarter();
    String getIndustryName();
    Long getCurrentQuarterSales();
    Double getIncreaseRate();
}

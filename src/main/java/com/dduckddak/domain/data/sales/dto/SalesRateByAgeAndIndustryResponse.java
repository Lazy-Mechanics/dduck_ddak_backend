package com.dduckddak.domain.data.sales.dto;

public interface SalesRateByAgeAndIndustryResponse {
    String getTownName();
    String getIndustryName();
    Double getAge10sSales();
    Double getAge20sSales();
    Double getAge30sSales();
    Double getAge40sSales();
    Double getAge50sSales();
    Double getAge60sAndMoreSales();
    Double getWomenPercentage();
    Long getTotalSales();
}

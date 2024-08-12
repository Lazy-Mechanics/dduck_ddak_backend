package com.dduckddak.domain.data.dto;


import com.dduckddak.domain.data.model.Sales;

public interface SalesTop10Response {
    String getTownName();
    Long getQuarter();
    Long getSales20234();
    Long getSales20241();
    Long getSalesDifference();
    float getIncreaseRate();
}

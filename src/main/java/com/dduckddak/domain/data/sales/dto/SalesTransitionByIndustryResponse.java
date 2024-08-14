package com.dduckddak.domain.data.sales.dto;

import java.util.List;

public record SalesTransitionByIndustryResponse(
        List<SalesData> salesList
) {
    public record SalesData(
            long quarter,
            long salesOfTown,
            int rankAtCity,
            long salesAvgOfCity,
            int rankAtDistrict,
            long salesAvgOfDistrict

    ) {
    }

    public static SalesTransitionByIndustryResponse from(List<SalesData> sales) {
        return new SalesTransitionByIndustryResponse(sales);
    }
}


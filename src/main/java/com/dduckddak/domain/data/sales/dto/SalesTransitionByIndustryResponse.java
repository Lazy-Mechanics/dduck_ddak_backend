package com.dduckddak.domain.data.sales.dto;

import java.util.List;

public record SalesTransitionByIndustryResponse(
        List<SalesData> salesList,
        int districtCount,
        long differenceFromPreviousQuarter,
        long differenceFromPreviousYear
) {
    public record SalesData(
            String townName,
            String industryName,
            long quarter,
            long salesOfTown,
            int rankAtCity,
            long salesAvgOfCity,
            int rankAtDistrict,
            long salesAvgOfDistrict

    ) {
    }

    public static SalesTransitionByIndustryResponse from(List<SalesData> sales, int districtCount, long differenceFromPreviousQuarter, long differenceFromPreviousYear) {
        return new SalesTransitionByIndustryResponse(sales, districtCount, differenceFromPreviousQuarter, differenceFromPreviousYear);
    }
}


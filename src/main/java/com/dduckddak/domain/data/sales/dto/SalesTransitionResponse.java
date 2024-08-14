package com.dduckddak.domain.data.sales.dto;

import java.util.List;

public record SalesTransitionResponse(
        List<SalesData> salesList,
        int districtCount,
        long differenceFromPreviousQuarter,
        long differenceFromPreviousYear
) {
    public record SalesData(
            String townName,
            long quarter,
            long salesOfTown,
            int rankAtCity,
            long salesAvgOfCity,
            int rankAtDistrict,
            long salesAvgOfDistrict

    ) {
    }

    public static SalesTransitionResponse from(List<SalesData> sales, int districtCount, long differenceFromPreviousQuarter, long differenceFromPreviousYear) {
        return new SalesTransitionResponse(sales, districtCount, differenceFromPreviousQuarter, differenceFromPreviousYear);
    }
}


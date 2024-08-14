package com.dduckddak.domain.data.sales.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SalesTop10OfIndustryResponse {
    private String townName;
    private String industryName;
    private Long quarter;
    private Long sales20234;
    private Long sales20241;
    private Long salesDifference;
    private float increaseRate;

    @Builder
    public SalesTop10OfIndustryResponse(String townName, String industryName, Long quarter, Long sales20234, Long sales20241, Long salesDifference, float increaseRate) {
        this.townName = townName;
        this.industryName = industryName;
        this.quarter = quarter;
        this.sales20234 = sales20234;
        this.sales20241 = sales20241;
        this.salesDifference = salesDifference;
        this.increaseRate = increaseRate;
    }
}
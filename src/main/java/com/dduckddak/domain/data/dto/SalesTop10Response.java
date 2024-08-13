package com.dduckddak.domain.data.dto;


import com.dduckddak.domain.data.model.Sales;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SalesTop10Response {
    private String townName;
    private Long quarter;
    private Long sales20234;
    private Long sales20241;
    private Long salesDifference;
    private float increaseRate;

    @Builder
    public SalesTop10Response(String townName, Long quarter, Long sales20234, Long sales20241, Long salesDifference, float increaseRate) {
        this.townName = townName;
        this.quarter = quarter;
        this.sales20234 = sales20234;
        this.sales20241 = sales20241;
        this.salesDifference = salesDifference;
        this.increaseRate = increaseRate;
    }
}
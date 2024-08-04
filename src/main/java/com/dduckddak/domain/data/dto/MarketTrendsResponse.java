package com.dduckddak.domain.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MarketTrendsResponse {
    private Long businessPeriod;

    @Builder
    public MarketTrendsResponse(Long businessPeriod) {
        this.businessPeriod = businessPeriod;
    }
}

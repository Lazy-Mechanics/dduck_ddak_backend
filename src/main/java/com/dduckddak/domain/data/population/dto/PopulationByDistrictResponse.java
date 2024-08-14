package com.dduckddak.domain.data.population.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopulationByDistrictResponse {
    private String quarter;
    private String count;

    @QueryProjection
    public PopulationByDistrictResponse(String quarter, String count) {
        this.quarter = quarter;
        this.count = count;
    }
}

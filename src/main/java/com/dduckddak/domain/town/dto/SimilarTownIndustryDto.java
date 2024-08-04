package com.dduckddak.domain.town.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimilarTownIndustryDto {
    private String quarter;
    private String count;

    @QueryProjection
    public SimilarTownIndustryDto(String quarter, String count) {
        this.quarter = quarter;
        this.count = count;
    }
}

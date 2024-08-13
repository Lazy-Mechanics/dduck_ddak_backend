package com.dduckddak.domain.data.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesForTransitionData {
    private String townName;
    private String townCode;
    private String quarter;
    private Long salesAtTown;

    @QueryProjection
    public SalesForTransitionData(String townName, String townCode, String quarter, Long salesAtTown) {
        this.townName = townName;
        this.townCode = townCode;
        this.quarter = quarter;
        this.salesAtTown = salesAtTown;
    }
}

package com.dduckddak.domain.data.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecentlySalesDto {

    private String quarter;
    private Long hour_0_6;
    private Long hour_6_11;
    private Long hour_11_14;
    private Long hour_14_17;
    private Long hour_17_21;
    private Long hour_21_24;

    @QueryProjection

    public RecentlySalesDto(Long hour_0_6, Long hour_11_14, Long hour_14_17, Long hour_17_21, Long hour_21_24, Long hour_6_11, String quarter) {
        this.hour_0_6 = hour_0_6;
        this.hour_11_14 = hour_11_14;
        this.hour_14_17 = hour_14_17;
        this.hour_17_21 = hour_17_21;
        this.hour_21_24 = hour_21_24;
        this.hour_6_11 = hour_6_11;
        this.quarter = quarter;
    }
}

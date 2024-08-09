package com.dduckddak.domain.member.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapResponse {

    private Long townCode;
    private String townName;
    private String industryName;
    private Long quarter;

    public ScrapResponse(Long townCode, String townName, String industryName, Long quarter) {
        this.townCode = townCode;
        this.townName = townName;
        this.industryName = industryName;
        this.quarter = quarter;
    }
}

package com.dduckddak.domain.member.model;

import com.dduckddak.domain.member.controller.dto.ScrapRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long townCode;
    private String industryName;
    private Long quarter;

    public Scrap(ScrapRequest scrapRequest) {
        this.townCode = scrapRequest.getTownCode();
        this.industryName = scrapRequest.getIndustryName();
        this.quarter = scrapRequest.getQuarter();
    }
}

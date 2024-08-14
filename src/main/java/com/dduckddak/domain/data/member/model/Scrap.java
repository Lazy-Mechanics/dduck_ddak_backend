package com.dduckddak.domain.data.member.model;

import com.dduckddak.domain.data.member.controller.dto.ScrapRequest;
import jakarta.persistence.*;
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
    private Long quarter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Scrap(ScrapRequest scrapRequest, Member member) {
        this.townCode = scrapRequest.getTownCode();
        this.quarter = scrapRequest.getQuarter();
        this.member = member;
    }
}

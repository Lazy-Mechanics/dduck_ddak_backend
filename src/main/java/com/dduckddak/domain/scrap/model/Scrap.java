package com.dduckddak.domain.scrap.model;

import com.dduckddak.domain.member.model.Member;
import com.dduckddak.domain.town.model.TownIndustry;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TownIndustry townIndustry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Scrap(TownIndustry townIndustry, Member member) {
        this.townIndustry = townIndustry;
        this.member = member;
    }

    public static Scrap of(TownIndustry townIndustry, Member member) {
        return new Scrap(townIndustry, member);
    }
}

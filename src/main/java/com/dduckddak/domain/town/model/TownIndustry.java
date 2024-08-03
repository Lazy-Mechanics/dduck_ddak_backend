package com.dduckddak.domain.town.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TownIndustry {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Town town;

    @ManyToOne(fetch = FetchType.LAZY)
    private Industry industry;

    public TownIndustry(long id, Industry industry, Town town) {
        this.id = id;
        this.industry = industry;
        this.town = town;
    }
}

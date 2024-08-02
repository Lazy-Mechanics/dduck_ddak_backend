package com.dduckddak.domain.town.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Getter
public class TownIndustryId implements Serializable {

    private Town town;
    private Industry industry;

    @Builder
    public TownIndustryId(Town town, Industry industry) {
        this.town = town;
        this.industry = industry;
    }

    public static TownIndustryId of(Town town, Industry industry) {
        return new TownIndustryId(town, industry);
    }

}

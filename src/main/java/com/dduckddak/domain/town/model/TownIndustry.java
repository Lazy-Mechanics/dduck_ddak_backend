package com.dduckddak.domain.town.model;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TownIndustry {

    @EmbeddedId
    private TownIndustryId townIndustryId;


    public static TownIndustry of(TownIndustryId townIndustryId) {
        return new TownIndustry(townIndustryId);
    }



}

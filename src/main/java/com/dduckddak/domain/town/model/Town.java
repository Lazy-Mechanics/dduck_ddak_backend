package com.dduckddak.domain.town.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Town {

    @EmbeddedId
    private TownId townId;

    private String area;
    private String name;
    private String code;

    @Builder
    public Town(TownId townId, String area, String name, String code) {
        this.townId = townId;
        this.area = area;
        this.name = name;
        this.code = code;
    }

    public static Town of(TownId townId, String area, String name, String code) {
        return Town.builder()
                .townId(townId)
                .area(area)
                .name(name)
                .code(code)
                .build();
    }

}

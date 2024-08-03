package com.dduckddak.domain.data.model;

import lombok.Getter;

@Getter
public enum PopulationType {

    FloatingPopulation("유동인구"),
    ResidentPopulation("상주인구"),
    WorkingPopulation("직장인구");

    private String name;

    PopulationType(String name) {
        this.name = name;
    }

    public static PopulationType of(String name) {
        for (PopulationType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No such PopulationType: " + name);  // 추후 Custom Exception으로 변경
    }
}

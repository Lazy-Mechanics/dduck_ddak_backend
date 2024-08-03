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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quarter;
    private String area;
    private String name;
    private String code;

    public Town(String area, String code, String name, Long quarter) {
        this.area = area;
        this.code = code;
        this.name = name;
        this.quarter = quarter;
    }
}

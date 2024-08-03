package com.dduckddak.domain.town.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
//@Table(uniqueConstraints = {
//        @UniqueConstraint(
//                name = "uk_town_name_quarter",
//                columnNames = {"name", "quarter"}
//        )
//})
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

    @Builder
    public Town(Long id, Long quarter, String area, String name, String code) {
        this.id = id;
        this.quarter = quarter;
        this.area = area;
        this.name = name;
        this.code = code;
    }

}

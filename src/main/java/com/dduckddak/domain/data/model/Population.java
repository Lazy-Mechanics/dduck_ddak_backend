package com.dduckddak.domain.data.model;

import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.model.TownIndustry;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PopulationType populationType;

    private Long totalPopulation;

    private Long mondayPopulation;
    private Long tuesdayPopulation;
    private Long wednesdayPopulation;
    private Long thursdayPopulation;
    private Long fridayPopulation;
    private Long saturdayPopulation;
    private Long sundayPopulation;

    private Long hour_0_6;
    private Long hour_6_11;
    private Long hour_11_14;
    private Long hour_14_17;
    private Long hour_17_21;
    private Long hour_21_24;

    private Long menPopulation;
    private Long womenPopulation;

    private Long age10sPopulation;
    private Long age20sPopulation;
    private Long age30sPopulation;
    private Long age40sPopulation;
    private Long age50sPopulation;
    private Long age60sAndMorePopulation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Town town;

    @Builder
    public Population(Long age10sPopulation, Long age20sPopulation, Long age30sPopulation, Long age40sPopulation, Long age50sPopulation, Long age60sAndMorePopulation, Long fridayPopulation, Long hour_0_6, Long hour_11_14, Long hour_14_17, Long hour_17_21, Long hour_21_24, Long hour_6_11, Long id, Long menPopulation, Long mondayPopulation, PopulationType populationType, Long saturdayPopulation, Long sundayPopulation, Long thursdayPopulation, Long totalPopulation, Town town, Long tuesdayPopulation, Long wednesdayPopulation, Long womenPopulation) {
        this.age10sPopulation = age10sPopulation;
        this.age20sPopulation = age20sPopulation;
        this.age30sPopulation = age30sPopulation;
        this.age40sPopulation = age40sPopulation;
        this.age50sPopulation = age50sPopulation;
        this.age60sAndMorePopulation = age60sAndMorePopulation;
        this.fridayPopulation = fridayPopulation;
        this.hour_0_6 = hour_0_6;
        this.hour_11_14 = hour_11_14;
        this.hour_14_17 = hour_14_17;
        this.hour_17_21 = hour_17_21;
        this.hour_21_24 = hour_21_24;
        this.hour_6_11 = hour_6_11;
        this.id = id;
        this.menPopulation = menPopulation;
        this.mondayPopulation = mondayPopulation;
        this.populationType = populationType;
        this.saturdayPopulation = saturdayPopulation;
        this.sundayPopulation = sundayPopulation;
        this.thursdayPopulation = thursdayPopulation;
        this.totalPopulation = totalPopulation;
        this.town = town;
        this.tuesdayPopulation = tuesdayPopulation;
        this.wednesdayPopulation = wednesdayPopulation;
        this.womenPopulation = womenPopulation;
    }
}

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
    private Long age10sPopulationOfFemale;
    private Long age10sPopulationOfMale;

    private Long age20sPopulation;
    private Long age20sPopulationOfFemale;
    private Long age20sPopulationOfMale;

    private Long age30sPopulation;
    private Long age30sPopulationOfFemale;
    private Long age30sPopulationOfMale;

    private Long age40sPopulation;
    private Long age40sPopulationOfFemale;
    private Long age40sPopulationOfMale;

    private Long age50sPopulation;
    private Long age50sPopulationOfFemale;
    private Long age50sPopulationOfMale;

    private Long age60sAndMorePopulation;
    private Long age60sPopulationOfFemale;
    private Long age60sPopulationOfMale;

    @ManyToOne(fetch = FetchType.LAZY)
    private Town town;

    @Builder
    public Population(Long id, PopulationType populationType, Long totalPopulation, Long mondayPopulation, Long tuesdayPopulation, Long wednesdayPopulation, Long thursdayPopulation, Long fridayPopulation, Long saturdayPopulation, Long sundayPopulation, Long hour_0_6, Long hour_6_11, Long hour_11_14, Long hour_14_17, Long hour_17_21, Long hour_21_24, Long menPopulation, Long womenPopulation, Long age10sPopulation, Long age10sPopulationOfFemale, Long age10sPopulationOfMale, Long age20sPopulation, Long age20sPopulationOfFemale, Long age20sPopulationOfMale, Long age30sPopulation, Long age30sPopulationOfFemale, Long age30sPopulationOfMale, Long age40sPopulation, Long age40sPopulationOfFemale, Long age40sPopulationOfMale, Long age50sPopulation, Long age50sPopulationOfFemale, Long age50sPopulationOfMale, Long age60sAndMorePopulation, Long age60sPopulationOfFemale, Long age60sPopulationOfMale, Town town) {
        this.id = id;
        this.populationType = populationType;
        this.totalPopulation = totalPopulation;
        this.mondayPopulation = mondayPopulation;
        this.tuesdayPopulation = tuesdayPopulation;
        this.wednesdayPopulation = wednesdayPopulation;
        this.thursdayPopulation = thursdayPopulation;
        this.fridayPopulation = fridayPopulation;
        this.saturdayPopulation = saturdayPopulation;
        this.sundayPopulation = sundayPopulation;
        this.hour_0_6 = hour_0_6;
        this.hour_6_11 = hour_6_11;
        this.hour_11_14 = hour_11_14;
        this.hour_14_17 = hour_14_17;
        this.hour_17_21 = hour_17_21;
        this.hour_21_24 = hour_21_24;
        this.menPopulation = menPopulation;
        this.womenPopulation = womenPopulation;
        this.age10sPopulation = age10sPopulation;
        this.age10sPopulationOfFemale = age10sPopulationOfFemale;
        this.age10sPopulationOfMale = age10sPopulationOfMale;
        this.age20sPopulation = age20sPopulation;
        this.age20sPopulationOfFemale = age20sPopulationOfFemale;
        this.age20sPopulationOfMale = age20sPopulationOfMale;
        this.age30sPopulation = age30sPopulation;
        this.age30sPopulationOfFemale = age30sPopulationOfFemale;
        this.age30sPopulationOfMale = age30sPopulationOfMale;
        this.age40sPopulation = age40sPopulation;
        this.age40sPopulationOfFemale = age40sPopulationOfFemale;
        this.age40sPopulationOfMale = age40sPopulationOfMale;
        this.age50sPopulation = age50sPopulation;
        this.age50sPopulationOfFemale = age50sPopulationOfFemale;
        this.age50sPopulationOfMale = age50sPopulationOfMale;
        this.age60sAndMorePopulation = age60sAndMorePopulation;
        this.age60sPopulationOfFemale = age60sPopulationOfFemale;
        this.age60sPopulationOfMale = age60sPopulationOfMale;
        this.town = town;
    }
}

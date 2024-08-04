package com.dduckddak.domain.data.dto;

import com.dduckddak.domain.data.model.Population;

import java.util.List;

public record PopulationByQuarterDto(
        List<QuarterPopulationDto> populationList
) {
    public record QuarterPopulationDto(
            Long quarter,
            Long population
    ) {
        public static QuarterPopulationDto from(Population population) {
            return new QuarterPopulationDto(
                    population.getTown().getQuarter(),
                    population.getTotalPopulation()
            );
        }
    }

    public static PopulationByQuarterDto from(List<Population> populations) {
        return new PopulationByQuarterDto(
                populations.stream().map(QuarterPopulationDto::from).toList()
        );
    }
}


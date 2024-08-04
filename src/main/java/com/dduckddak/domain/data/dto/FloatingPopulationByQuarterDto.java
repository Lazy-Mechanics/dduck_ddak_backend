package com.dduckddak.domain.data.dto;

import com.dduckddak.domain.data.model.Population;

import java.util.List;

public record FloatingPopulationByQuarterDto(
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

    public static FloatingPopulationByQuarterDto from(List<Population> populations) {
        return new FloatingPopulationByQuarterDto(
                populations.stream().map(QuarterPopulationDto::from).toList()
        );
    }
}


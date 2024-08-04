package com.dduckddak.domain.data.dto;

import com.dduckddak.domain.data.model.Population;

public record FloatingPopulationByTimeDto(
        Long hour_0_6,
        Long hour_6_11,
        Long hour_11_14,
        Long hour_14_17,
        Long hour_17_21,
        Long hour_21_24
) {

    public static FloatingPopulationByTimeDto from(Population population) {
        return new FloatingPopulationByTimeDto(
                population.getHour_0_6(),
                population.getHour_6_11(),
                population.getHour_11_14(),
                population.getHour_14_17(),
                population.getHour_17_21(),
                population.getHour_21_24()
        );
    }
}

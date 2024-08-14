package com.dduckddak.domain.data.population.dto;

import java.util.List;

public record PopulationTransitionResponse(
        List<PopulationData> populationList
) {
    public record PopulationData(
            String townName,
            long quarter,
            long populationOfTown,
            int rankAtCity,
            long populationAvgOfCity,
            int rankAtDistrict,
            long populationAvgOfDistrict

    ) {
    }

    public static PopulationTransitionResponse from(List<PopulationData> populations) {
        return new PopulationTransitionResponse(populations);
    }
}


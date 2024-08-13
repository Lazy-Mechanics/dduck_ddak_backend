package com.dduckddak.domain.data.dto;

import com.dduckddak.domain.data.model.Population;

import java.util.List;

public record PopulationTransitionResponse(
        List<PopulationData> populationList
) {
    public record PopulationData(
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


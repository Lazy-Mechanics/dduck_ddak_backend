package com.dduckddak.domain.data.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PopulationsTop10Response {
    private String townName;
    private Long populations;
    private Long populationsDifference;
    private float increaseRate;

    @Builder
    public PopulationsTop10Response(String townName, Long populations, Long populationsDifference, float increaseRate) {
        this.townName = townName;
        this.populations = populations;
        this.populationsDifference = populationsDifference;
        this.increaseRate = increaseRate;
    }
}

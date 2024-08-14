package com.dduckddak.domain.town.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StoreCountTop10Response {
    private String townName;
    private Long storeCount20234;
    private Long storeCount20241;
    private Long storeCountDifference;
    private float increaseRate;

    @Builder
    public StoreCountTop10Response(String townName, Long storeCount20234, Long storeCount20241, Long storeCountDifference, float increaseRate) {
        this.townName = townName;
        this.storeCount20234 = storeCount20234;
        this.storeCount20241 = storeCount20241;
        this.storeCountDifference = storeCountDifference;
        this.increaseRate = increaseRate;
    }
}
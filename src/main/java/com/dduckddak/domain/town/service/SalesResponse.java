package com.dduckddak.domain.town.service;

import com.dduckddak.domain.data.model.Sales;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SalesResponse {

    private Long monday;
    private Long tuesday;
    private Long Wednesday;
    private Long thursday;
    private Long friday;
    private Long saturday;
    private Long sunday;

    @Builder
    public SalesResponse(Long monday, Long tuesday, Long wednesday, Long thursday,
                         Long friday, Long saturday, Long sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        Wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }
    public static SalesResponse of(Sales sales){
        return SalesResponse.builder()
                .monday(sales.getMondaySales())
                .tuesday(sales.getTuesdaySales())
                .wednesday(sales.getWednesdaySales())
                .thursday(sales.getThursdaySales())
                .friday(sales.getFridaySales())
                .saturday(sales.getSaturdaySales())
                .sunday(sales.getSundaySales())
                .build();
    }
}

package com.dduckddak.domain.town.dto;

import com.dduckddak.domain.data.model.Sales;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SalesResponse {

    private Double monday;
    private Double tuesday;
    private Double Wednesday;
    private Double thursday;
    private Double friday;
    private Double saturday;
    private Double sunday;

    @Builder
    public SalesResponse(Double monday, Double tuesday, Double wednesday, Double thursday,
                         Double friday, Double saturday, Double sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        Wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }
    public static SalesResponse of(Sales sales) {
        // 전체 주 매출의 합을 계산
        long totalWeeklySales = sales.getMondaySales() + sales.getTuesdaySales() + sales.getWednesdaySales() +
                sales.getThursdaySales() + sales.getFridaySales() + sales.getSaturdaySales() +
                sales.getSundaySales();

        // 매출이 전혀 없는 경우를 처리 (0으로 나누는 것을 방지)
        if (totalWeeklySales == 0) {
            return SalesResponse.builder()
                    .monday(0.0)
                    .tuesday(0.0)
                    .wednesday(0.0)
                    .thursday(0.0)
                    .friday(0.0)
                    .saturday(0.0)
                    .sunday(0.0)
                    .build();
        }

        // 각 요일별 매출을 전체 매출 대비 백분율로 변환
        return SalesResponse.builder()
                .monday(Double.parseDouble(String.format("%.1f", 100.0 * sales.getMondaySales() / totalWeeklySales)))
                .tuesday(Double.parseDouble(String.format("%.1f", 100.0 * sales.getTuesdaySales() / totalWeeklySales)))
                .wednesday(Double.parseDouble(String.format("%.1f", 100.0 * sales.getWednesdaySales() / totalWeeklySales)))
                .thursday(Double.parseDouble(String.format("%.1f", 100.0 * sales.getThursdaySales() / totalWeeklySales)))
                .friday(Double.parseDouble(String.format("%.1f", 100.0 * sales.getFridaySales() / totalWeeklySales)))
                .saturday(Double.parseDouble(String.format("%.1f", 100.0 * sales.getSaturdaySales() / totalWeeklySales)))
                .sunday(Double.parseDouble(String.format("%.1f", 100.0 * sales.getSundaySales() / totalWeeklySales)))
                .build();
    }
}

package com.dduckddak.domain.data.dto;

import com.dduckddak.domain.data.model.Population;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record TimelyDto(
        Long hour_0_6,
        Long hour_6_11,
        Long hour_11_14,
        Long hour_14_17,
        Long hour_17_21,
        Long hour_21_24
) {

    private static final Logger log = LoggerFactory.getLogger(TimelyDto.class);

    public static TimelyDto from(Population population) {
        return new TimelyDto(
                population.getHour_0_6(),
                population.getHour_6_11(),
                population.getHour_11_14(),
                population.getHour_14_17(),
                population.getHour_17_21(),
                population.getHour_21_24()
        );
    }

    public static TimelyDto from(RecentlySalesDto recentlySalesDto) {

        return new TimelyDto(
                recentlySalesDto.getHour_0_6(),
                recentlySalesDto.getHour_6_11(),
                recentlySalesDto.getHour_11_14(),
                recentlySalesDto.getHour_14_17(),
                recentlySalesDto.getHour_17_21(),
                recentlySalesDto.getHour_21_24()
        );
    }
}

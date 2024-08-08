package com.dduckddak.domain.town.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SalesVO {

        private Long monday;
        private Long tuesday;
        private Long wednesday;
        private Long thursday;
        private Long friday;
        private Long saturday;
        private Long sunday;

        @QueryProjection
        public SalesVO(Long monday, Long tuesday, Long wednesday, Long thursday,
                       Long friday, Long saturday, Long sunday) {
            this.monday = monday;
            this.tuesday = tuesday;
            this.wednesday = wednesday;
            this.thursday = thursday;
            this.friday = friday;
            this.saturday = saturday;
            this.sunday = sunday;
        }
}

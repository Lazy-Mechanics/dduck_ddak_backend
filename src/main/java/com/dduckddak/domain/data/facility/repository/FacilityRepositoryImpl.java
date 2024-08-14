package com.dduckddak.domain.data.facility.repository;

import com.dduckddak.domain.data.facility.dto.FacilityByDistrictResponse;
import com.dduckddak.domain.data.facility.dto.QFacilityByDistrictResponse;
import com.dduckddak.domain.data.facility.model.Facility;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.dduckddak.domain.data.facility.model.QFacility.facility;
import static com.dduckddak.domain.town.model.QTown.town;

@RequiredArgsConstructor
public class FacilityRepositoryImpl implements FacilityRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Facility findByTownCodeAndQuarter(String code) {
        JPAQuery<Facility> query = queryFactory
                .selectFrom(facility)
                .join(facility.town, town).fetchJoin()
                .where(town.code.eq(code)
                        .and(town.quarter.eq(20241L))); // 최신데이터

        return query.fetchOne();
    }

    @Override
    public FacilityByDistrictResponse findRecentByDistrict(String district) {
        return queryFactory
                .select(new QFacilityByDistrictResponse(
                        facility.governmentOfficeCnt.sum(),
                        facility.bankCnt.sum(),
                        facility.hospitalCnt.sum(),
                        facility.pharmacyCnt.sum(),
                        facility.departmentStoreCnt.sum(),
                        Expressions.asNumber(
                                facility.elementarySchoolCnt.sum()
                                        .add(facility.highSchoolCnt.sum())
                                        .add(facility.middleSchoolCnt.sum())
                        ).as("totalSchoolCnt"),
                        facility.accommodationFacilityCnt.sum(),
                        Expressions.asNumber(
                                facility.airportCnt.sum()
                                    .add(facility.busStopCnt.sum())
                                    .add(facility.subwayStationCnt.sum())
                        ).as("transportationFacilityCnt")
                ))
                .from(facility)
                .join(facility.town, town)
                .where(
                        town.name.contains(district)
                        ,(town.quarter.eq(20241L)) // 최신데이터
                )
                .groupBy(town.quarter)
                .orderBy(town.quarter.desc())
                .fetchOne();
    }
}

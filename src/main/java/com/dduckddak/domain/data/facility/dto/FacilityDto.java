package com.dduckddak.domain.data.facility.dto;


import com.dduckddak.domain.data.facility.model.Facility;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FacilityDto {

    private Long governmentOfficeCnt; // 관공서
    private Long bankCnt; // 은행
    private Long hospitalCnt; // 병원
    private Long pharmacyCnt; // 약국
    private Long departmentStore; // 백화점
    private Long schoolCnt; // 학교
    private Long accommodationFacilityCnt; // 숙박
    private Long transportationFacilityCnt; // 교통

    public static FacilityDto of(Facility facility){
        return FacilityDto.builder()
                .governmentOfficeCnt(facility.getGovernmentOfficeCnt())
                .bankCnt(facility.getBankCnt())
                .hospitalCnt(facility.getHospitalCnt())
                .pharmacyCnt(facility.getPharmacyCnt())
                .departmentStore(facility.getDepartmentStoreCnt())
                .schoolCnt(facility.getElementarySchoolCnt() + facility.getHighSchoolCnt() + facility.getMiddleSchoolCnt())
                .accommodationFacilityCnt(facility.getAccommodationFacilityCnt())
                .transportationFacilityCnt(facility.getAirportCnt() + facility.getBusStopCnt() + facility.getSubwayStationCnt())
                .build();
    }

}

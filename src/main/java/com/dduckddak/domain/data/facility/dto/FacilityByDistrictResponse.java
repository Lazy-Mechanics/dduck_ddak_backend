package com.dduckddak.domain.data.facility.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FacilityByDistrictResponse {

    private Long governmentOfficeCnt; // 관공서
    private Long bankCnt; // 은행
    private Long hospitalCnt; // 병원
    private Long pharmacyCnt; // 약국
    private Long departmentStore; // 백화점
    private Long schoolCnt; // 학교
    private Long accommodationFacilityCnt; // 숙박
    private Long transportationFacilityCnt; // 교통

    @QueryProjection
    public FacilityByDistrictResponse(Long governmentOfficeCnt, Long bankCnt, Long hospitalCnt, Long pharmacyCnt, Long departmentStore, Long schoolCnt, Long accommodationFacilityCnt, Long transportationFacilityCnt) {
        this.governmentOfficeCnt = governmentOfficeCnt;
        this.bankCnt = bankCnt;
        this.hospitalCnt = hospitalCnt;
        this.pharmacyCnt = pharmacyCnt;
        this.departmentStore = departmentStore;
        this.schoolCnt = schoolCnt;
        this.accommodationFacilityCnt = accommodationFacilityCnt;
        this.transportationFacilityCnt = transportationFacilityCnt;
    }
}

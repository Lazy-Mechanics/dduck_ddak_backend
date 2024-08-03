package com.dduckddak.domain.data.model;

import com.dduckddak.domain.town.model.Town;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Town town;

    private Long publicAttractionFacilityCnt;
    private Long governmentOfficeCnt;
    private Long bankCnt;
    private Long generalHospitalCnt;
    private Long hospitalCnt;
    private Long pharmacyCnt;
    private Long kindergartenCnt;
    private Long elementarySchoolCnt;
    private Long middleSchoolCnt;
    private Long highSchoolCnt;
    private Long universityCnt;
    private Long departmentStoreCnt;
    private Long supermarketCnt;
    private Long theaterCnt;
    private Long accommodationFacilityCnt;
    private Long airportCnt;
    private Long railwayStationCnt;
    private Long busTerminalCnt;
    private Long subwayStationCnt;
    private Long busStopCnt;

    public Facility(Long accommodationFacilityCnt, Long airportCnt, Long bankCnt, Long busStopCnt, Long busTerminalCnt, Long departmentStoreCnt,
                    Long elementarySchoolCnt, Long generalHospitalCnt, Long governmentOfficeCnt, Long highSchoolCnt, Long hospitalCnt,
                    Long kindergartenCnt, Long middleSchoolCnt, Long pharmacyCnt, Long publicAttractionFacilityCnt, Long railwayStationCnt,
                    Long subwayStationCnt, Long supermarketCnt, Long theaterCnt, Town town, Long universityCnt) {
        this.accommodationFacilityCnt = accommodationFacilityCnt;
        this.airportCnt = airportCnt;
        this.bankCnt = bankCnt;
        this.busStopCnt = busStopCnt;
        this.busTerminalCnt = busTerminalCnt;
        this.departmentStoreCnt = departmentStoreCnt;
        this.elementarySchoolCnt = elementarySchoolCnt;
        this.generalHospitalCnt = generalHospitalCnt;
        this.governmentOfficeCnt = governmentOfficeCnt;
        this.highSchoolCnt = highSchoolCnt;
        this.hospitalCnt = hospitalCnt;
        this.kindergartenCnt = kindergartenCnt;
        this.middleSchoolCnt = middleSchoolCnt;
        this.pharmacyCnt = pharmacyCnt;
        this.publicAttractionFacilityCnt = publicAttractionFacilityCnt;
        this.railwayStationCnt = railwayStationCnt;
        this.subwayStationCnt = subwayStationCnt;
        this.supermarketCnt = supermarketCnt;
        this.theaterCnt = theaterCnt;
        this.town = town;
        this.universityCnt = universityCnt;
    }
}

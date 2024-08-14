package com.dduckddak.domain.data.facility.repository;

import com.dduckddak.domain.data.facility.model.Facility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FacilityBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Facility> facilities) {
        String sql = "INSERT INTO facility (" +
                "accommodation_facility_cnt, airport_cnt, bank_cnt, bus_stop_cnt, " +
                "bus_terminal_cnt, department_store_cnt, elementary_school_cnt, " +
                "general_hospital_cnt, government_office_cnt, high_school_cnt, hospital_cnt, kindergarten_cnt, middle_school_cnt, " +
                "pharmacy_cnt, public_attraction_facility_cnt, " +
                "railway_station_cnt, subway_station_cnt, supermarket_cnt, theater_cnt, university_cnt, town_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                facilities,
                facilities.size(),
                (PreparedStatement pstmt, Facility facility) -> {
                    pstmt.setLong(1, facility.getAccommodationFacilityCnt());
                    pstmt.setLong(2, facility.getAirportCnt());
                    pstmt.setLong(3, facility.getBankCnt());
                    pstmt.setLong(4, facility.getBusStopCnt());
                    pstmt.setLong(5, facility.getBusTerminalCnt());
                    pstmt.setLong(6, facility.getDepartmentStoreCnt());
                    pstmt.setLong(7, facility.getElementarySchoolCnt());
                    pstmt.setLong(8, facility.getGeneralHospitalCnt());
                    pstmt.setLong(9, facility.getGovernmentOfficeCnt());
                    pstmt.setLong(10, facility.getHighSchoolCnt());
                    pstmt.setLong(11, facility.getHospitalCnt());
                    pstmt.setLong(12, facility.getKindergartenCnt());
                    pstmt.setLong(13, facility.getMiddleSchoolCnt());
                    pstmt.setLong(14, facility.getPharmacyCnt());
                    pstmt.setLong(15, facility.getPublicAttractionFacilityCnt());
                    pstmt.setLong(16, facility.getRailwayStationCnt());
                    pstmt.setLong(17, facility.getSubwayStationCnt());
                    pstmt.setLong(18, facility.getSupermarketCnt());
                    pstmt.setLong(19, facility.getTheaterCnt());
                    pstmt.setLong(20, facility.getUniversityCnt());
                    pstmt.setLong(21, facility.getTown().getId()); // 가정: town 객체에서 ID를 가져옴
                });
    }
}

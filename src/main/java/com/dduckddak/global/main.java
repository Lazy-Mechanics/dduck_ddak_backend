package com.dduckddak.global;

import com.dduckddak.domain.data.model.*;
import com.dduckddak.domain.data.repository.*;
import com.dduckddak.domain.town.model.Industry;
import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.model.TownIndustry;
import com.dduckddak.domain.town.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class main implements ApplicationRunner {

    private final TownRepository townRepository;
    private final IndustryRepository industryRepository;
    private final TownIndustryRepository townIndustryRepository;
    private final PopulationBulkRepository populationBulkRepository;
    private final TownBulkRepository townBulkRepository;
    private final FinanceBulkRepository financeBulkRepository;
    private final FacilityBulkRepository fiFacilityBulkRepository;
    private final EstimateSalesBulkRepository estimateSalesBulkRepository;
    private final IndustryBulkRepository industryBulkRepository;
    private final TownIndustryBulkRepository townIndustryBulkRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("시작");

        // JSON 파일 읽기
        JSONParser parser = new JSONParser();


        /**
         * 행정동 데이터
         */
        Reader dong = new FileReader("src/main/resources/json/dongCenter.json");
        JSONArray dongJson = (JSONArray) parser.parse(dong);

        List<Town> townList = new ArrayList<>();
        for (Object datum : dongJson) {
            JSONObject building = (JSONObject) datum;

            for (int year = 2019; year <= 2024; year++) {
                for (int quarter = 1; quarter <= 4; quarter++) {
                    Town town = Town.builder()
                            .code((String) building.get("adm_cd"))
                            .name((String) building.get("name"))
                            .quarter(Long.parseLong(String.valueOf(year) + String.valueOf(quarter)))
                            .build();
                    townList.add(town);
                }
            }
        }
        townBulkRepository.saveAll(townList);
        log.info(String.valueOf(townList.size()));
        log.info("행정동 끝");

/*

        Reader FloatingPopulation = new FileReader("src/main/resources/json/FloatingPopulation.json");
        JSONObject FloatingPopulationJson = (JSONObject) parser.parse(FloatingPopulation);
        JSONArray FloatingPopulationJsonData = (JSONArray) FloatingPopulationJson.get("DATA");

        List<Population> populationList = new ArrayList<>(); // 유동인구

        // 업종
        Reader jumpo = new FileReader("src/main/resources/json/store.json");
        JSONObject store = (JSONObject) parser.parse(jumpo);
        JSONArray storeData = (JSONArray) store.get("DATA");
        List<Industry> industries = new ArrayList<>();
        for (Object datum : storeData) {
            JSONObject industry = (JSONObject) datum;
            if (industries.stream().filter(n -> n.getCode().equals((String) industry.get("svc_induty_cd"))).count() == 0)
                industries.add(new Industry((String) industry.get("svc_induty_cd")));
        }
        industryBulkRepository.saveAll(industries);
        log.info("업종 끝");

        *//**
         * 추정 매출 데이터
         *//*
        Reader estimateSales = new FileReader("src/main/resources/json/estimatedSales.json");
        JSONObject estimateSalesJson = (JSONObject) parser.parse(estimateSales);
        JSONArray estimateSalesData = (JSONArray) estimateSalesJson.get("DATA");

        List<Sales> sales = new ArrayList<>();
        List<TownIndustry> townIndustryList = new ArrayList<>();
        long num = 1L;
        for (Object datum : estimateSalesData) {
            JSONObject estimate = (JSONObject) datum;

            Town town = townList.stream().filter(t -> t.getCode().equals((String) estimate.get("adstrd_cd"))).findFirst().orElseThrow();
//            Town town = townRepository.findByCode((String) estimate.get("adstrd_cd")).orElseThrow();

            //Industry industry = industryRepository.findByCode("svc_induty_cd").orElseThrow();
            Industry industry = industries.stream().filter(i -> i.getCode().equals((String) estimate.get("svc_induty_cd"))).findFirst().orElseThrow();

            TownIndustry townIndustry = new TownIndustry(num++, industry, town);

            sales.add(new Sales(
                    parseLongFromObject(estimate.get("thsmon_selng_amt")),
                    parseLongFromObject(estimate.get("mon_selng_amt")),
                    parseLongFromObject(estimate.get("tues_selng_amt")),
                    parseLongFromObject(estimate.get("wed_selng_amt")),
                    parseLongFromObject(estimate.get("thur_selng_amt")),
                    parseLongFromObject(estimate.get("fri_selng_amt")),
                    parseLongFromObject(estimate.get("sat_selng_amt")),
                    parseLongFromObject(estimate.get("sun_selng_amt")),
                    parseLongFromObject(estimate.get("mdwk_selng_amt")),
                    parseLongFromObject(estimate.get("wkend_selng_amt")),
                    parseLongFromObject(estimate.get("tmzon_00_06_selng_amt")),
                    parseLongFromObject(estimate.get("tmzon_06_11_selng_amt")),
                    parseLongFromObject(estimate.get("tmzon_11_14_selng_amt")),
                    parseLongFromObject(estimate.get("tmzon_14_17_selng_amt")),
                    parseLongFromObject(estimate.get("tmzon_17_21_selng_amt")),
                    parseLongFromObject(estimate.get("tmzon_21_24_selng_amt")),
                    parseLongFromObject(estimate.get("ml_selng_amt")),
                    parseLongFromObject(estimate.get("fml_selng_amt")),
                    parseLongFromObject(estimate.get("agrde_10_selng_amt")),
                    parseLongFromObject(estimate.get("agrde_20_selng_amt")),
                    parseLongFromObject(estimate.get("agrde_30_selng_amt")),
                    parseLongFromObject(estimate.get("agrde_40_selng_amt")),
                    parseLongFromObject(estimate.get("agrde_50_selng_amt")),
                    parseLongFromObject(estimate.get("agrde_60_above_selng_amt")),
                    townIndustry
            ));
        }

        townIndustryBulkRepository.saveAll(townIndustryList);

        log.info("추정매출");

        List<Finance> financeList = new ArrayList<>();
        *//**
         * 소득소비
         *//*
        Reader incomeConsumption = new FileReader("src/main/resources/json/finance.json");
        JSONObject financeJson = (JSONObject) parser.parse(incomeConsumption);
        JSONArray financeData = (JSONArray) financeJson.get("DATA");


        for (Object datum: financeData) {
            JSONObject finance = (JSONObject) datum;

            Town town = townList.stream()
                    .filter(t -> t.getName().equals((String) finance.get("adstrd_cd_nm")))
                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) finance.get("stdr_yyqu_cd"))))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Town not found"));

//            Town town = townRepository.findByNameAndQuarter((String) finance.get("adstrd_cd_nm"), Long.parseLong((String) finance.get("stdr_yyqu_cd")))
//                    .orElseThrow(IllegalArgumentException::new);

            Finance fin = Finance.builder()
                    .averageMonthlyIncome(Long.parseLong((String) finance.get("avg_income")))
                    .totalExpenditure(Long.parseLong((String) finance.get("tot_expenditure")))
                    .groceryExpenditure(Long.parseLong((String) finance.get("grocery_expenditure")))
                    .clothingExpenditure(Long.parseLong((String) finance.get("clothing_expenditure")))
                    .householdGoodsExpenditure(Long.parseLong((String) finance.get("household_goods_expenditure")))
                    .medicalExpenditure(Long.parseLong((String) finance.get("medical_expenditure")))
                    .transportationExpenditure(Long.parseLong((String) finance.get("transportation_expenditure")))
                    .educationExpenditure(Long.parseLong((String) finance.get("education_expenditure")))
                    .entertainmentExpenditure(Long.parseLong((String) finance.get("entertainment_expenditure")))
                    .leisureCultureExpenditure(Long.parseLong((String) finance.get("leisure_culture_expenditure")))
                    .foodExpenditure(Long.parseLong((String) finance.get("food_expenditure")))
                    .town(town)
                    .build();

            financeList.add(fin);
        }
        financeBulkRepository.saveAll(financeList);
        log.info("소득소비");

        *//**
          *  유동인구
          *//*
        for (Object datum : FloatingPopulationJsonData) {
            JSONObject building = (JSONObject) datum;

            Town town = townList.stream()
                    .filter(t -> t.getName().equals((String) building.get("adstrd_cd_nm")))
                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) building.get("stdr_yyqu_cd"))))
                    .findFirst().orElseThrow();

//            Town town = townRepository.findByNameAndQuarter((String) building.get("adstrd_cd_nm"), Long.parseLong((String) building.get("stdr_yyqu_cd"))).orElseThrow(IllegalArgumentException::new);

            // 유동인구 Population 생성
            Population population = Population.builder()
                    .populationType(PopulationType.FloatingPopulation)
                    .totalPopulation(parseLongFromObject(building.get("tot_flpop_co")))
                    .menPopulation(parseLongFromObject(building.get("ml_flpop_co")))
                    .womenPopulation(parseLongFromObject(building.get("fml_flpop_co")))
                    .age10sPopulation(parseLongFromObject(building.get("agrde_10_flpop_co")))
                    .age20sPopulation(parseLongFromObject(building.get("agrde_20_flpop_co")))
                    .age30sPopulation(parseLongFromObject(building.get("agrde_30_flpop_co")))
                    .age40sPopulation(parseLongFromObject(building.get("agrde_40_flpop_co")))
                    .age50sPopulation(parseLongFromObject(building.get("agrde_50_flpop_co")))
                    .age60sAndMorePopulation(parseLongFromObject(building.get("agrde_60_above_flpop_co")))
                    .mondayPopulation(parseLongFromObject(building.get("mon_flpop_co")))
                    .tuesdayPopulation(parseLongFromObject(building.get("tues_flpop_co")))
                    .wednesdayPopulation(parseLongFromObject(building.get("wed_flpop_co")))
                    .thursdayPopulation(parseLongFromObject(building.get("thur_flpop_co")))
                    .fridayPopulation(parseLongFromObject(building.get("fri_flpop_co")))
                    .saturdayPopulation(parseLongFromObject(building.get("sat_flpop_co")))
                    .sundayPopulation(parseLongFromObject(building.get("sun_flpop_co")))
                    .hour_0_6(parseLongFromObject(building.get("tmzon_00_06_flpop_co")))
                    .hour_6_11(parseLongFromObject(building.get("tmzon_06_11_flpop_co")))
                    .hour_11_14(parseLongFromObject(building.get("tmzon_11_14_flpop_co")))
                    .hour_14_17(parseLongFromObject(building.get("tmzon_14_17_flpop_co")))
                    .hour_17_21(parseLongFromObject(building.get("tmzon_17_21_flpop_co")))
                    .hour_21_24(parseLongFromObject(building.get("tmzon_21_24_flpop_co")))
                    .town(town)
                    .build();

            populationList.add(population);
        }


        *//**
         * 상주인구
         *//*
        Reader residentPopulation = new FileReader("src/main/resources/json/residentPopulation.json");
        JSONObject residentJSON = (JSONObject) parser.parse(residentPopulation);
        JSONArray residentData = (JSONArray) residentJSON.get("DATA");

        for (Object datum : residentData) {
            JSONObject regident = (JSONObject) datum;

            Town town = townList.stream()
                    .filter(t -> t.getName().equals((String) regident.get("adstrd_cd_nm")))
                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) regident.get("stdr_yyqu_cd"))))
                    .findFirst().orElseThrow();

            Population population = Population.builder()
                    .populationType(PopulationType.ResidentPopulation)
                    .totalPopulation(Long.parseLong((String) regident.get("tot_repop_co")))
                    .menPopulation(Long.parseLong((String) regident.get("ml_repop_co")))
                    .womenPopulation(Long.parseLong((String) regident.get("fml_repop_co")))
                    .age10sPopulation(Long.parseLong((String) regident.get("agrde_10_repop_co")))
                    .age20sPopulation(Long.parseLong((String) regident.get("agrde_20_repop_co")))
                    .age30sPopulation(Long.parseLong((String) regident.get("agrde_30_repop_co")))
                    .age40sPopulation(Long.parseLong((String) regident.get("agrde_40_repop_co")))
                    .age50sPopulation(Long.parseLong((String) regident.get("agrde_50_repop_co")))
                    .age60sAndMorePopulation(Long.parseLong((String) regident.get("agrde_60_above_repop_co")))
                    .mondayPopulation(Long.parseLong((String) regident.get("mon_repop_co")))
                    .tuesdayPopulation(Long.parseLong((String) regident.get("tues_repop_co")))
                    .wednesdayPopulation(Long.parseLong((String) regident.get("wed_repop_co")))
                    .thursdayPopulation(Long.parseLong((String) regident.get("thur_repop_co")))
                    .fridayPopulation(Long.parseLong((String) regident.get("fri_repop_co")))
                    .saturdayPopulation(Long.parseLong((String) regident.get("sat_repop_co")))
                    .sundayPopulation(Long.parseLong((String) regident.get("sun_repop_co")))
                    .hour_0_6(Long.parseLong((String) regident.get("tmzon_00_06_repop_co")))
                    .hour_6_11(Long.parseLong((String) regident.get("tmzon_06_11_repop_co")))
                    .hour_11_14(Long.parseLong((String) regident.get("tmzon_11_14_repop_co")))
                    .hour_14_17(Long.parseLong((String) regident.get("tmzon_14_17_repop_co")))
                    .hour_17_21(Long.parseLong((String) regident.get("tmzon_17_21_repop_co")))
                    .hour_21_24(Long.parseLong((String) regident.get("tmzon_21_24_repop_co")))
                    .town(town)
                    .build();

            populationList.add(population);

        }


        *//**
         * 직장인구
         *//*
        Reader workingPopulation = new FileReader("src/main/resources/json/finance.json");
        JSONObject workingPopulationJson = (JSONObject) parser.parse(workingPopulation);
        JSONArray workingPopulationData = (JSONArray) workingPopulationJson.get("DATA");

        for (Object datum : workingPopulationData) {
            JSONObject workingPopulationObj = (JSONObject) datum;

            Town town = townList.stream()
                    .filter(t -> t.getName().equals((String) workingPopulationObj.get("adstrd_cd_nm")))
                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) workingPopulationObj.get("stdr_yyqu_cd"))))
                    .findFirst().orElseThrow();

//            Town town = townRepository.findByNameAndQuarter((String) workingPopulationObj.get("adstrd_cd_nm"), Long.parseLong((String) workingPopulationObj.get("stdr_yyqu_cd")))
//                    .orElseThrow(IllegalArgumentException::new);

            Population population = Population.builder()
                    .populationType(PopulationType.WorkingPopulation)
                    .totalPopulation(Long.parseLong((String) workingPopulationObj.get("tot_wrc_popltn_co")))
                    .menPopulation(Long.parseLong((String) workingPopulationObj.get("ml_wrc_popltn_co")))
                    .womenPopulation(Long.parseLong((String) workingPopulationObj.get("fml_wrc_popltn_co")))
                    .age10sPopulation(Long.parseLong((String) workingPopulationObj.get("agrde_10_wrc_popltn_co")))
                    .age20sPopulation(Long.parseLong((String) workingPopulationObj.get("agrde_20_wrc_popltn_co")))
                    .age30sPopulation(Long.parseLong((String) workingPopulationObj.get("agrde_30_wrc_popltn_co")))
                    .age40sPopulation(Long.parseLong((String) workingPopulationObj.get("agrde_40_wrc_popltn_co")))
                    .age50sPopulation(Long.parseLong((String) workingPopulationObj.get("agrde_50_wrc_popltn_co")))
                    .age60sAndMorePopulation(Long.parseLong((String) workingPopulationObj.get("agrde_60_above_wrc_popltn_co")))
                    .town(town)
                    .build();

            populationList.add(population);
        }

        populationBulkRepository.saveAll(populationList);
        log.info("인구데이터 끝");

        // 집객시설
        Reader attractingCustomersPopulation = new FileReader("src/main/resources/json/facility.json");
        JSONObject attractingCustomersPopulationJson = (JSONObject) parser.parse(attractingCustomersPopulation);
        JSONArray attractingCustomersPopulationData = (JSONArray) attractingCustomersPopulationJson.get("DATA");
        List<Facility> facilityList = new ArrayList<>();
        for (Object datum : attractingCustomersPopulationData) {
            JSONObject attractingCustomersPopulationObj = (JSONObject) datum;

            Town town = townList.stream()
                    .filter(t -> t.getName().equals((String) attractingCustomersPopulationObj.get("adstrd_cd_nm")))
                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) attractingCustomersPopulationObj.get("stdr_yyqu_cd"))))
                    .findFirst().orElseThrow();

//            Town town = townRepository.findByNameAndQuarter((String) attractingCustomersPopulationObj.get("adstrd_cd_nm"), Long.parseLong((String) attractingCustomersPopulationObj.get("stdr_yyqu_cd")))
//                    .orElseThrow(IllegalArgumentException::new);

            Facility facility = Facility.builder()
                    .publicAttractionFacilityCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("viatr_fclty_co")))
                    .governmentOfficeCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("pblofc_co")))
                    .bankCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("bank_co")))
                    .generalHospitalCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("gnrl_hptl_co")))
                    .hospitalCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("gnrl_hsptl_co")))
                    .pharmacyCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("phrmcy_co")))
                    .kindergartenCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("kndrgr_co")))
                    .elementarySchoolCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("elesch_co")))
                    .middleSchoolCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("mskul_co")))
                    .highSchoolCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("hgschl_co")))
                    .universityCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("univ_co")))
                    .departmentStoreCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("drts_co")))
                    .supermarketCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("supmk_co")))
                    .theaterCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("theat_co")))
                    .accommodationFacilityCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("staying_fclty_co")))
                    .airportCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("arprt_co")))
                    .railwayStationCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("rlroad_statn_co")))
                    .busTerminalCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("bus_trminl_co")))
                    .subwayStationCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("subway_stain_co")))
                    .busStopCnt(Long.parseLong((String) attractingCustomersPopulationObj.get("bus_sttn_co")))
                    .town(town)
                    .build();
            facilityList.add(facility);
        }
        fiFacilityBulkRepository.saveAll(facilityList);
        log.info("끝");
        estimateSalesBulkRepository.saveAll(sales);*/
    }

    private static Long parseLongFromObject(Object obj) {
        if (obj == null) return 0L;
        String numericOnly = obj.toString().replaceAll("[^0-9]", "");
        return Long.parseLong(numericOnly);
    }
}


/*
*
ML_FLPOP_CO:"남성_유동인구_수"
TMZON_17_21_FLPOP_CO:"시간대_17_21_유동인구_수"
SUN_FLPOP_CO:"일요일_유동인구_수"
TOT_FLPOP_CO:"총_유동인구_수"
TMZON_21_24_FLPOP_CO:"시간대_21_24_유동인구_수"
TMZON_11_14_FLPOP_CO:"시간대_11_14_유동인구_수"
THUR_FLPOP_CO:"목요일_유동인구_수"
TMZON_00_06_FLPOP_CO:"시간대_00_06_유동인구_수"
ADSTRD_CD_NM:"행정동_코드_명"
ADSTRD_CD:"행정동_코드"
FRI_FLPOP_CO:"금요일_유동인구_수"
STDR_YYQU_CD:"기준_년분기_코드"
WED_FLPOP_CO:"수요일_유동인구_수"
AGRDE_20_FLPOP_CO:"연령대_20_유동인구_수"
SAT_FLPOP_CO:"토요일_유동인구_수"
TMZON_14_17_FLPOP_CO:"시간대_14_17_유동인구_수"
AGRDE_40_FLPOP_CO:"연령대_40_유동인구_수"
AGRDE_30_FLPOP_CO:"연령대_30_유동인구_수"
AGRDE_60_ABOVE_FLPOP_CO:"연령대_60_이상_유동인구_수"
TMZON_06_11_FLPOP_CO:"시간대_06_11_유동인구_수"
FML_FLPOP_CO:"여성_유동인구_수"
AGRDE_10_FLPOP_CO:"연령대_10_유동인구_수"
AGRDE_50_FLPOP_CO:"연령대_50_유동인구_수"
MON_FLPOP_CO:"월요일_유동인구_수"
TUES_FLPOP_CO:"화요일_유동인구_수"

* */
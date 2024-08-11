//package com.dduckddak.global;
//
//import com.dduckddak.domain.data.model.*;
//import com.dduckddak.domain.data.repository.*;
//import com.dduckddak.domain.member.model.Member;
//import com.dduckddak.domain.member.model.MemberRole;
//import com.dduckddak.domain.member.repository.MemberRepository;
//import com.dduckddak.domain.town.model.Industry;
//import com.dduckddak.domain.town.model.Town;
//import com.dduckddak.domain.town.model.TownIndustry;
//import com.dduckddak.domain.town.repository.*;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.io.FileReader;
//import java.io.Reader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@Transactional
//@Profile("local")
//public class main implements ApplicationRunner {
//    private final TownRepository townRepository;
//    private final IndustryRepository industryRepository;
//    private final TownIndustryRepository townIndustryRepository;
//    private final PopulationBulkRepository populationBulkRepository;
//    private final TownBulkRepository townBulkRepository;
//    private final FinanceBulkRepository financeBulkRepository;
//    private final FacilityBulkRepository facilityBulkRepository;
//    private final EstimateSalesBulkRepository estimateSalesBulkRepository;
//    private final IndustryBulkRepository industryBulkRepository;
//    private final TownIndustryBulkRepository townIndustryBulkRepository;
//    private final MemberRepository memberRepository;
//    private final MarketTrendsBulkRepository marketTrendsBulkRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        log.info("시작");
//
//        if (townRepository.count() > 0) {
//            log.info("데이터가 이미 존재합니다.");
//            return;
//        }
//
//        memberRepository.save(Member.builder()
//                .nickname("이수호")
//                .email("hyeri0603@naver.com")
//                .memberRole(MemberRole.MEMBER).build());
//
//        // JSON 파일 읽기
//        JSONParser parser = new JSONParser();
//
//        /**
//         *  행정동 데이터
//         **/
//        List<Town> townList = new ArrayList<>();
//        for (Object datum : (JSONArray) parser.parse(new FileReader("src/main/resources/json/dongCenter.json"))) {
//            JSONObject building = (JSONObject) datum;
//
//            for (int year = 2019; year <= 2024; year++) {
//                for (int quarter = 1; quarter <= 4; quarter++) {
//                    Town town = Town.builder()
//                            .code((String) building.get("adm_cd"))
//                            .name((String) building.get("name"))
//                            .quarter(Long.parseLong(String.valueOf(year) + String.valueOf(quarter)))
//                            .build();
//                    townList.add(town);
//                }
//            }
//        }
//        townBulkRepository.saveAll(townList);
//        List<Town> towns = townRepository.findAll();
//        log.info("행정동 끝");
//
//        // 업종
//        Reader jumpo = new FileReader("src/main/resources/json/store.json");
//        JSONObject store = (JSONObject) parser.parse(jumpo);
//
//        List<Industry> industries = new ArrayList<>();
//        for (Object datum : (JSONArray) store.get("DATA")) {
//            JSONObject industry = (JSONObject) datum;
//            if (industries.stream().noneMatch(n -> n.getCode().equals((String) industry.get("svc_induty_cd"))))
//                industries.add(new Industry((String) industry.get("svc_induty_cd"), (String) industry.get("svc_induty_cd_nm")));
//        }
//        industryBulkRepository.saveAll(industries);
//        industries = industryRepository.findAll();
//        log.info("업종 끝");
//
//        // 추정 매출 데이터
//        Reader stores = new FileReader("src/main/resources/json/store.json");
//        JSONObject storeJson = (JSONObject) parser.parse(stores);
//
//        List<Sales> sales = new ArrayList<>();
//        List<TownIndustry> townIndustryList = new ArrayList<>();
//        for (Object datum : (JSONArray) storeJson.get("DATA")) {
//            JSONObject storeObj = (JSONObject) datum;
//
//            Town town = towns.stream().filter(t -> t.getCode().equals(storeObj.get("adstrd_cd")))
//                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) storeObj.get("stdr_yyqu_cd"))))
//                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Town not found"));
//
//            Industry industry = industries.stream().filter(i -> i.getCode().equals(storeObj.get("svc_induty_cd")))
//                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Industry not found"));
//
//            townIndustryList.add(TownIndustry.builder()
//                    .town(town)
//                    .industry(industry)
//                    .storeCount(parseLongFromObject(storeObj.get("stor_co")))
//                    .similarStoreCount(parseLongFromObject(storeObj.get("similr_induty_stor_co")))
//                    .openStoreCount(parseLongFromObject(storeObj.get("opbiz_stor_co")))
//                    .closeStoreCount(parseLongFromObject(storeObj.get("clsbiz_stor_co")))
//                    .build());
//        }
//        townIndustryBulkRepository.saveAll(townIndustryList);
//        List<TownIndustry> townIndustries = townIndustryRepository.findAll();
//        log.info("행정동 별 업종 끝");
//
//        Reader estimateSales = new FileReader("src/main/resources/json/estimatedSales.json");
//        JSONObject estimateSalesJson = (JSONObject) parser.parse(estimateSales);
//        int cnt = 0, idx = 0;
//        for (Object datum : (JSONArray) estimateSalesJson.get("DATA")) {
//            JSONObject estimate = (JSONObject) datum;
//
//            Town town = towns.stream().filter(t -> t.getCode().equals((String) estimate.get("adstrd_cd")))
//                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) estimate.get("stdr_yyqu_cd"))))
//                    .findFirst().orElseThrow(() -> new RuntimeException("Town not found"));
//
//            Industry industry = industries.stream().filter(i -> i.getCode().equals((String) estimate.get("svc_induty_cd")))
//                    .findFirst().orElseThrow(() -> new RuntimeException("Industry not found"));
//
//            TownIndustry townIndustry = townIndustries.stream().filter(t -> t.getTown().getId().equals(town.getId()))
//                    .filter(t -> t.getIndustry().getId().equals(industry.getId()))
//                    .findFirst().orElseThrow(() -> new RuntimeException("TownIndustry not found"));
//
//            cnt++;
//            sales.add(new Sales(
//                    parseLongFromObject(estimate.get("thsmon_selng_amt")),
//                    parseLongFromObject(estimate.get("mon_selng_amt")),
//                    parseLongFromObject(estimate.get("tues_selng_amt")),
//                    parseLongFromObject(estimate.get("wed_selng_amt")),
//                    parseLongFromObject(estimate.get("thur_selng_amt")),
//                    parseLongFromObject(estimate.get("fri_selng_amt")),
//                    parseLongFromObject(estimate.get("sat_selng_amt")),
//                    parseLongFromObject(estimate.get("sun_selng_amt")),
//                    parseLongFromObject(estimate.get("mdwk_selng_amt")),
//                    parseLongFromObject(estimate.get("wkend_selng_amt")),
//                    parseLongFromObject(estimate.get("tmzon_00_06_selng_amt")),
//                    parseLongFromObject(estimate.get("tmzon_06_11_selng_amt")),
//                    parseLongFromObject(estimate.get("tmzon_11_14_selng_amt")),
//                    parseLongFromObject(estimate.get("tmzon_14_17_selng_amt")),
//                    parseLongFromObject(estimate.get("tmzon_17_21_selng_amt")),
//                    parseLongFromObject(estimate.get("tmzon_21_24_selng_amt")),
//                    parseLongFromObject(estimate.get("ml_selng_amt")),
//                    parseLongFromObject(estimate.get("fml_selng_amt")),
//                    parseLongFromObject(estimate.get("agrde_10_selng_amt")),
//                    parseLongFromObject(estimate.get("agrde_20_selng_amt")),
//                    parseLongFromObject(estimate.get("agrde_30_selng_amt")),
//                    parseLongFromObject(estimate.get("agrde_40_selng_amt")),
//                    parseLongFromObject(estimate.get("agrde_50_selng_amt")),
//                    parseLongFromObject(estimate.get("agrde_60_above_selng_amt")),
//                    townIndustry
//            ));
//
//            if(cnt == 10000) {
//                log.info(String.valueOf(++idx));
//                estimateSalesBulkRepository.saveAll(sales);
//                cnt = 0;
//                sales = new ArrayList<>();
//            }
//
//        }
//        estimateSalesBulkRepository.saveAll(sales);
//        log.info("추정매출 끝");
//
//         // 소득소비
//        List<Finance> financeList = new ArrayList<>();
//        JSONObject financeJson = (JSONObject) parser.parse(new FileReader("src/main/resources/json/finance.json"));
//        for (Object datum :  (JSONArray) financeJson.get("DATA")) {
//            JSONObject finance = (JSONObject) datum;
//
//            Town town = towns.stream().filter(t -> t.getCode().equals((String) finance.get("adstrd_cd")))
//                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) finance.get("stdr_yyqu_cd"))))
//                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Town not found"));
//
//            Finance fin = Finance.builder()
//                    .averageMonthlyIncome(parseLongFromObject(finance.get("mt_avrg_income_amt")))
//                    .totalExpenditure(parseLongFromObject(finance.get("expndtr_totamt")))
//                    .groceryExpenditure(parseLongFromObject(finance.get("fdstffs_expndtr_totamt")))
//                    .clothingExpenditure(parseLongFromObject(finance.get("clths_ftwr_expndtr_totamt")))
//                    .householdGoodsExpenditure(parseLongFromObject(finance.get("lvspl_expndtr_totamt")))
//                    .medicalExpenditure(parseLongFromObject(finance.get("mcp_expndtr_totamt")))
//                    .transportationExpenditure(parseLongFromObject(finance.get("trnsport_expndtr_totamt")))
//                    .educationExpenditure(parseLongFromObject(finance.get("edc_expndtr_totamt")))
//                    .entertainmentExpenditure(parseLongFromObject(finance.get("plesr_expndtr_totamt")))
//                    .leisureCultureExpenditure(parseLongFromObject(finance.get("lsr_cltur_expndtr_totamt")))
//                    .foodExpenditure(parseLongFromObject(finance.get("fd_expndtr_totamt")))
//                    .town(town)
//                    .build();
//
//            financeList.add(fin);
//        }
//        financeBulkRepository.saveAll(financeList);
//        log.info("소득소비 끝");
//
//        // 유동인구
//        List<Population> populationList = new ArrayList<>();
//        JSONObject FloatingPopulationJson = (JSONObject) parser.parse(new FileReader("src/main/resources/json/FloatingPopulation.json"));
//        for (Object datum : (JSONArray) FloatingPopulationJson.get("DATA")) {
//            JSONObject building = (JSONObject) datum;
//
//            Town town = towns.stream().filter(t -> t.getCode().equals((String) building.get("adstrd_cd")))
//                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) building.get("stdr_yyqu_cd"))))
//                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Town not found"));
//
//            // 유동인구 Population 생성
//            Population population = Population.builder()
//                    .populationType(PopulationType.FloatingPopulation)
//                    .totalPopulation(parseLongFromObject(building.get("tot_flpop_co")))
//                    .menPopulation(parseLongFromObject(building.get("ml_flpop_co")))
//                    .womenPopulation(parseLongFromObject(building.get("fml_flpop_co")))
//                    .age10sPopulation(parseLongFromObject(building.get("agrde_10_flpop_co")))
//                    .age20sPopulation(parseLongFromObject(building.get("agrde_20_flpop_co")))
//                    .age30sPopulation(parseLongFromObject(building.get("agrde_30_flpop_co")))
//                    .age40sPopulation(parseLongFromObject(building.get("agrde_40_flpop_co")))
//                    .age50sPopulation(parseLongFromObject(building.get("agrde_50_flpop_co")))
//                    .age60sAndMorePopulation(parseLongFromObject(building.get("agrde_60_above_flpop_co")))
//                    .mondayPopulation(parseLongFromObject(building.get("mon_flpop_co")))
//                    .tuesdayPopulation(parseLongFromObject(building.get("tues_flpop_co")))
//                    .wednesdayPopulation(parseLongFromObject(building.get("wed_flpop_co")))
//                    .thursdayPopulation(parseLongFromObject(building.get("thur_flpop_co")))
//                    .fridayPopulation(parseLongFromObject(building.get("fri_flpop_co")))
//                    .saturdayPopulation(parseLongFromObject(building.get("sat_flpop_co")))
//                    .sundayPopulation(parseLongFromObject(building.get("sun_flpop_co")))
//                    .hour_0_6(parseLongFromObject(building.get("tmzon_00_06_flpop_co")))
//                    .hour_6_11(parseLongFromObject(building.get("tmzon_06_11_flpop_co")))
//                    .hour_11_14(parseLongFromObject(building.get("tmzon_11_14_flpop_co")))
//                    .hour_14_17(parseLongFromObject(building.get("tmzon_14_17_flpop_co")))
//                    .hour_17_21(parseLongFromObject(building.get("tmzon_17_21_flpop_co")))
//                    .hour_21_24(parseLongFromObject(building.get("tmzon_21_24_flpop_co")))
//                    .town(town)
//                    .build();
//
//            populationList.add(population);
//        }
//        populationBulkRepository.saveAll(populationList);
//
//        // 상주인구
//        populationList = new ArrayList<>();
//        JSONObject residentJSON = (JSONObject) parser.parse(new FileReader("src/main/resources/json/residentPopulation.json"));
//        for (Object datum : (JSONArray) residentJSON.get("DATA")) {
//            JSONObject regident = (JSONObject) datum;
//
//            Town town = towns.stream().filter(t -> t.getCode().equals((String) regident.get("adstrd_cd")))
//                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) regident.get("stdr_yyqu_cd"))))
//                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Town not found"));
//
//            Population population = Population.builder()
//                    .populationType(PopulationType.ResidentPopulation)
//                    .totalPopulation(parseLongFromObject(regident.get("tot_repop_co")))
//                    .menPopulation(parseLongFromObject(regident.get("ml_repop_co")))
//                    .womenPopulation(parseLongFromObject(regident.get("fml_repop_co")))
//                    .age10sPopulation(parseLongFromObject(regident.get("agrde_10_repop_co")))
//                    .age20sPopulation(parseLongFromObject(regident.get("agrde_20_repop_co")))
//                    .age30sPopulation(parseLongFromObject(regident.get("agrde_30_repop_co")))
//                    .age40sPopulation(parseLongFromObject(regident.get("agrde_40_repop_co")))
//                    .age50sPopulation(parseLongFromObject(regident.get("agrde_50_repop_co")))
//                    .age60sAndMorePopulation(parseLongFromObject(regident.get("agrde_60_above_repop_co")))
//                    .town(town)
//                    .build();
//
//            populationList.add(population);
//        }
//
//         // 직장인구
//        JSONObject workingPopulationJson = (JSONObject) parser.parse(new FileReader("src/main/resources/json/workingPopulation.json"));
//        for (Object datum : (JSONArray) workingPopulationJson.get("DATA")) {
//            JSONObject workingPopulationObj = (JSONObject) datum;
//
//            Town town = towns.stream()
//                    .filter(t -> t.getCode().equals((String) workingPopulationObj.get("adstrd_cd")))
//                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) workingPopulationObj.get("stdr_yyqu_cd"))))
//                    .findFirst().orElseThrow();
//
//            Population population = Population.builder()
//                    .populationType(PopulationType.WorkingPopulation)
//                    .totalPopulation(parseLongFromObject(workingPopulationObj.get("tot_wrc_popltn_co")))
//                    .menPopulation(parseLongFromObject(workingPopulationObj.get("ml_wrc_popltn_co")))
//                    .womenPopulation(parseLongFromObject(workingPopulationObj.get("fml_wrc_popltn_co")))
//                    .age10sPopulation(parseLongFromObject(workingPopulationObj.get("agrde_10_wrc_popltn_co")))
//                    .age20sPopulation(parseLongFromObject(workingPopulationObj.get("agrde_20_wrc_popltn_co")))
//                    .age30sPopulation(parseLongFromObject(workingPopulationObj.get("agrde_30_wrc_popltn_co")))
//                    .age40sPopulation(parseLongFromObject(workingPopulationObj.get("agrde_40_wrc_popltn_co")))
//                    .age50sPopulation(parseLongFromObject(workingPopulationObj.get("agrde_50_wrc_popltn_co")))
//                    .age60sAndMorePopulation(parseLongFromObject(workingPopulationObj.get("agrde_60_above_wrc_popltn_co")))
//                    .town(town)
//                    .build();
//
//            populationList.add(population);
//        }
//        populationBulkRepository.saveAll(populationList, PopulationType.WorkingPopulation);
//        log.info("인구데이터 끝");
//
//        // 상권변화지표
//        List<MarketTrends> marketTrendsList = new ArrayList<>();
//        JSONObject indicateOfCommercialChangeJson = (JSONObject) parser.parse(new FileReader("src/main/resources/json/indicators_of_commercial_change.json"));
//        for (Object datum : (JSONArray) indicateOfCommercialChangeJson.get("DATA")) {
//            JSONObject indicateOfCommercialChangeObj = (JSONObject) datum;
//
//            Town town = towns.stream()
//                    .filter(t -> t.getCode().equals((String) indicateOfCommercialChangeObj.get("adstrd_cd")))
//                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) indicateOfCommercialChangeObj.get("stdr_yyqu_cd"))))
//                    .findFirst().orElseThrow();
//
//            marketTrendsList.add(MarketTrends.builder()
//                    .town(town)
//                    .tradeAreaChangeIndex((String) indicateOfCommercialChangeObj.get("trdar_chnge_ix"))
//                    .areaChangeIndexName((String) indicateOfCommercialChangeObj.get("trdar_chnge_ix_nm"))
//                    .operateSaleAvg(parseLongFromObject(indicateOfCommercialChangeObj.get("opr_sale_mt_avrg")))
//                    .closeSaleAvg(parseLongFromObject(indicateOfCommercialChangeObj.get("cls_sale_mt_avrg")))
//                    .operateSaleAvgBySeoul(parseLongFromObject(indicateOfCommercialChangeObj.get("su_opr_sale_mt_avrg")))
//                    .closeSaleAvgBySeoul(parseLongFromObject(indicateOfCommercialChangeObj.get("su_cls_sale_mt_avrg")))
//                    .build());
//        }
//        marketTrendsBulkRepository.saveAll(marketTrendsList);
//        log.info("상권변화지표 끝");
//
//        // 집객시설
//        List<Facility> facilityList = new ArrayList<>();
//        JSONObject attractingCustomersPopulationJson = (JSONObject) parser.parse(new FileReader("src/main/resources/json/facility.json"));
//        for (Object datum : (JSONArray) attractingCustomersPopulationJson.get("DATA")) {
//            JSONObject attractingCustomersPopulationObj = (JSONObject) datum;
//
//            Town town = towns.stream()
//                    .filter(t -> t.getCode().equals((String) attractingCustomersPopulationObj.get("adstrd_cd")))
//                    .filter(t -> t.getQuarter().equals(Long.parseLong((String) attractingCustomersPopulationObj.get("stdr_yyqu_cd"))))
//                    .findFirst().orElseThrow();
//
//            Facility facility = Facility.builder()
//                    .publicAttractionFacilityCnt(parseLongFromObject(attractingCustomersPopulationObj.get("viatr_fclty_co")))
//                    .governmentOfficeCnt(parseLongFromObject(attractingCustomersPopulationObj.get("pblofc_co")))
//                    .bankCnt(parseLongFromObject(attractingCustomersPopulationObj.get("bank_co")))
//                    .generalHospitalCnt(parseLongFromObject(attractingCustomersPopulationObj.get("gnrl_hptl_co")))
//                    .hospitalCnt(parseLongFromObject(attractingCustomersPopulationObj.get("gnrl_hsptl_co")))
//                    .pharmacyCnt(parseLongFromObject(attractingCustomersPopulationObj.get("parmacy_co")))
//                    .kindergartenCnt(parseLongFromObject(attractingCustomersPopulationObj.get("kndrgr_co")))
//                    .elementarySchoolCnt(parseLongFromObject(attractingCustomersPopulationObj.get("elesch_co")))
//                    .middleSchoolCnt(parseLongFromObject(attractingCustomersPopulationObj.get("mskul_co")))
//                    .highSchoolCnt(parseLongFromObject(attractingCustomersPopulationObj.get("hgschl_co")))
//                    .universityCnt(parseLongFromObject(attractingCustomersPopulationObj.get("univ_co")))
//                    .departmentStoreCnt(parseLongFromObject(attractingCustomersPopulationObj.get("drts_co")))
//                    .supermarketCnt(parseLongFromObject(attractingCustomersPopulationObj.get("supmk_co")))
//                    .theaterCnt(parseLongFromObject(attractingCustomersPopulationObj.get("theat_co")))
//                    .accommodationFacilityCnt(parseLongFromObject(attractingCustomersPopulationObj.get("stayng_fclty_co")))
//                    .airportCnt(parseLongFromObject(attractingCustomersPopulationObj.get("arprt_co")))
//                    .railwayStationCnt(parseLongFromObject(attractingCustomersPopulationObj.get("rlroad_statn_co")))
//                    .busTerminalCnt(parseLongFromObject(attractingCustomersPopulationObj.get("bus_trminl_co")))
//                    .subwayStationCnt(parseLongFromObject(attractingCustomersPopulationObj.get("subway_stain_co")))
//                    .busStopCnt(parseLongFromObject(attractingCustomersPopulationObj.get("bus_sttn_co")))
//                    .town(town)
//                    .build();
//
//            facilityList.add(facility);
//        }
//        facilityBulkRepository.saveAll(facilityList);
//        log.info("끝");
//    }
//
//    private static Long parseLongFromObject(Object obj) {
//        if (obj == null) return 0L;
//        String numericOnly = obj.toString().replaceAll("[^0-9]", "");
//        return Long.parseLong(numericOnly);
//    }
//}

package com.dduckddak.global;

import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.data.repository.PopulationRepository;
import com.dduckddak.domain.town.model.Industry;
import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.repository.IndustryRepository;
import com.dduckddak.domain.town.repository.TownRepository;
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

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class main implements ApplicationRunner {

    private final TownRepository townRepository;
    private final PopulationRepository populationRepository;
    private final IndustryRepository industryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("시작");
        JSONParser parser = new JSONParser();
        // JSON 파일 읽기
        Reader reader = new FileReader("src/main/resources/json/FloatingPopulation.json");

        JSONObject FloatingPopulation = (JSONObject) parser.parse(reader);

        JSONArray data = (JSONArray) FloatingPopulation.get("DATA");

        // 행정동 Town 생성
//        for (Object datum : data) {
//            JSONObject building = (JSONObject) datum;
//            Town town = Town.builder()
//                    .code((String) building.get("adstrd_cd"))
//                    .name((String) building.get("adstrd_cd_nm"))
//                    .quarter(Long.parseLong((String) building.get("stdr_yyqu_cd")))
//                    .build();
//
//            // 유동인구 Population 생성
//
//            Population population = Population.builder()
//                    .populationType(PopulationType.FloatingPopulation)
//                    .totalPopulation(Long.parseLong((String) building.get("tot_flpop_co")))
//                    .menPopulation(Long.parseLong((String) building.get("ml_flpop_co")))
//                    .womenPopulation(Long.parseLong((String) building.get("fml_flpop_co")))
//                    .age10sPopulation(Long.parseLong((String) building.get("agrde_10_flpop_co")))
//                    .age20sPopulation(Long.parseLong((String) building.get("agrde_20_flpop_co")))
//                    .age30sPopulation(Long.parseLong((String) building.get("agrde_30_flpop_co")))
//                    .age40sPopulation(Long.parseLong((String) building.get("agrde_40_flpop_co")))
//                    .age50sPopulation(Long.parseLong((String) building.get("agrde_50_flpop_co")))
//                    .age60sAndMorePopulation(Long.parseLong((String) building.get("agrde_60_above_flpop_co")))
//                    .mondayPopulation(Long.parseLong((String) building.get("mon_flpop_co")))
//                    .tuesdayPopulation(Long.parseLong((String) building.get("tues_flpop_co")))
//                    .wednesdayPopulation(Long.parseLong((String) building.get("wed_flpop_co")))
//                    .thursdayPopulation(Long.parseLong((String) building.get("thur_flpop_co")))
//                    .fridayPopulation(Long.parseLong((String) building.get("fri_flpop_co")))
//                    .saturdayPopulation(Long.parseLong((String) building.get("sat_flpop_co")))
//                    .sundayPopulation(Long.parseLong((String) building.get("sun_flpop_co")))
//                    .hour_0_6(Long.parseLong((String) building.get("tmzon_00_06_flpop_co")))
//                    .hour_6_11(Long.parseLong((String) building.get("tmzon_06_11_flpop_co")))
//                    .hour_11_14(Long.parseLong((String) building.get("tmzon_11_14_flpop_co")))
//                    .hour_14_17(Long.parseLong((String) building.get("tmzon_14_17_flpop_co")))
//                    .hour_17_21(Long.parseLong((String) building.get("tmzon_17_21_flpop_co")))
//                    .hour_21_24(Long.parseLong((String) building.get("tmzon_21_24_flpop_co")))
//                    .town(town)
//                    .build();
//
//            townRepository.save(town);
//            populationRepository.save(population);
//        }

        // 점포
        Reader jumpo = new FileReader("src/main/resources/json/store.json");
        JSONObject store = (JSONObject) parser.parse(jumpo);
        JSONArray storeData = (JSONArray) store.get("DATA");
        for (Object datum : storeData) {
            JSONObject industry = (JSONObject) datum;
            industryRepository.save(new Industry((String) industry.get("svc_induty_cd_nm")));
        }

        // 상주인구
        Reader residentPopulation = new FileReader("src/main/resources/json/residentPopulation.json");
        JSONObject residentJSON = (JSONObject) parser.parse(residentPopulation);
        JSONArray residentData = (JSONArray) residentJSON.get("DATA");
        /**
         * "MAG_40_REPOP_CO":"남성연령대_40_상주인구_수","FAG_20_REPOP_CO":"여성연령대_20_상주인구_수","AGRDE_30_REPOP_CO":"연령대_30_상주인구_수","AGRDE_60_ABOVE_REPOP_CO":"연령대_60_이상_상주인구_수","FAG_60_ABOVE_REPOP_CO":"여성연령대_60_이상_상주인구_수","ADSTRD_CD_NM":"행정동_코드_명","AGRDE_20_REPOP_CO":"연령대_20_상주인구_수","FML_REPOP_CO":"여성_상주인구_수","ADSTRD_CD":"행정동_코드","STDR_YYQU_CD":"기준_년분기_코드","FAG_10_REPOP_CO":"여성연령대_10_상주인구_수","MAG_20_REPOP_CO":"남성연령대_20_상주인구_수","AGRDE_50_REPOP_CO":"연령대_50_상주인구_수","APT_HSHLD_CO":"아파트_가구_수","MAG_10_REPOP_CO":"남성연령대_10_상주인구_수","TOT_REPOP_CO":"총_상주인구_수","FAG_50_REPOP_CO":"여성연령대_50_상주인구_수","TOT_HSHLD_CO":"총_가구_수","MAG_60_ABOVE_REPOP_CO":"남성연령대_60_이상_상주인구_수","ML_REPOP_CO":"남성_상주인구_수","AGRDE_10_REPOP_CO":"연령대_10_상주인구_수","NON_APT_HSHLD_CO":"비_아파트_가구_수","FAG_40_REPOP_CO":"여성연령대_40_상주인구_수","MAG_30_REPOP_CO":"남성연령대_30_상주인구_수","FAG_30_REPOP_CO":"여성연령대_30_상주인구_수","MAG_50_REPOP_CO":"남성연령대_50_상주인구_수","AGRDE_40_REPOP_CO":"연령대_40_상주인구_수"
         */
//        for (Object datum : residentData) {
//            JSONObject regident = (JSONObject) datum;
//
//            townRepository.findByNameAndQuarter()
//
//            Population population = Population.builder()
//                    .populationType(PopulationType.FloatingPopulation)
//                    .totalPopulation(Long.parseLong((String) regident.get("tot_repop_co")))
//                    .menPopulation(Long.parseLong((String) regident.get("ml_repop_co")))
//                    .womenPopulation(Long.parseLong((String) regident.get("fml_repop_co")))
//                    .age10sPopulation(Long.parseLong((String) regident.get("agrde_10_repop_co")))
//                    .age20sPopulation(Long.parseLong((String) regident.get("agrde_20_repop_co")))
//                    .age30sPopulation(Long.parseLong((String) regident.get("agrde_30_repop_co")))
//                    .age40sPopulation(Long.parseLong((String) regident.get("agrde_40_repop_co")))
//                    .age50sPopulation(Long.parseLong((String) regident.get("agrde_50_repop_co")))
//                    .age60sAndMorePopulation(Long.parseLong((String) regident.get("agrde_60_above_repop_co")))
//                    .mondayPopulation(Long.parseLong((String) regident.get("mon_repop_co")))
//                    .tuesdayPopulation(Long.parseLong((String) regident.get("tues_repop_co")))
//                    .wednesdayPopulation(Long.parseLong((String) regident.get("wed_repop_co")))
//                    .thursdayPopulation(Long.parseLong((String) regident.get("thur_repop_co")))
//                    .fridayPopulation(Long.parseLong((String) regident.get("fri_repop_co")))
//                    .saturdayPopulation(Long.parseLong((String) regident.get("sat_repop_co")))
//                    .sundayPopulation(Long.parseLong((String) regident.get("sun_repop_co")))
//                    .hour_0_6(Long.parseLong((String) regident.get("tmzon_00_06_repop_co")))
//                    .hour_6_11(Long.parseLong((String) regident.get("tmzon_06_11_repop_co")))
//                    .hour_11_14(Long.parseLong((String) regident.get("tmzon_11_14_repop_co")))
//                    .hour_14_17(Long.parseLong((String) regident.get("tmzon_14_17_repop_co")))
//                    .hour_17_21(Long.parseLong((String) regident.get("tmzon_17_21_repop_co")))
//                    .hour_21_24(Long.parseLong((String) regident.get("tmzon_21_24_repop_co")))
//                    .town(town)
//                    .build();
//        }

        // 추정매출
//        Reader estimateSales = new FileReader("src/main/resources/json/satimateSales.json");
//        JSONObject estimateSalesJson = (JSONObject) parser.parse(estimateSales);
//        JSONArray estimateSalesData = (JSONArray) estimateSalesJson.get("DATA");
//
//        for (Object datum : estimateSalesData) {
//            JSONObject estimate = (JSONObject) datum;
//
//            new Sales(
//
//            );
//
//        }
//        log.info("끝");
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
package com.dduckddak.domain.town.service;

import com.dduckddak.domain.data.dto.MarketAnalysisResponse;
import com.dduckddak.domain.data.dto.MarketTrendsResponse;
import com.dduckddak.domain.data.dto.SalesDiffVO;
import com.dduckddak.domain.data.model.MarketTrends;
import com.dduckddak.domain.data.model.Population;
import com.dduckddak.domain.data.model.PopulationType;
import com.dduckddak.domain.data.model.Sales;
import com.dduckddak.domain.data.repository.MarketTrendRepository;
import com.dduckddak.domain.data.repository.PopulationRepository;
import com.dduckddak.domain.data.repository.sales.SalesRepository;
import com.dduckddak.domain.town.dto.RecentlyTownIndustryResponse;
import com.dduckddak.domain.town.dto.SalesResponse;
import com.dduckddak.domain.town.dto.SalesVO;
import com.dduckddak.domain.town.dto.SimilarTownIndustryDto;
import com.dduckddak.domain.town.model.Industry;
import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.model.TownIndustry;
import com.dduckddak.domain.town.repository.IndustryRepository;
import com.dduckddak.domain.town.repository.TownIndustryRepository;
import com.dduckddak.domain.town.repository.TownRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TownIndustryService {
    private final TownRepository townRepository;
    private final IndustryRepository industryRepository;
    private final TownIndustryRepository townIndustryRepository;
    private final MarketTrendRepository marketTrendRepository;
    private final PopulationRepository populationRepository;
    private final SalesRepository salesRepository;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public List<RecentlyTownIndustryResponse> getRecentlyIndustries(int code, String name) {
        return townIndustryRepository.findTownIndustryByTownCodeAndQuarterAndName(code, name);
    }

    public List<RecentlyTownIndustryResponse> getRecentlyIndustriesInDistrict(String district, String name) {
        return townIndustryRepository.findTownIndustryByTownCodeAndQuarterAndNameInDistrict(district, name);
    }

    public List<SimilarTownIndustryDto> getSimilarIndustries(int code, String name) {
        return townIndustryRepository.findSimilarTownIndustryByTownCodeAndQuarterAndName(code, name);
    }

    public List<SimilarTownIndustryDto> getSimilarIndustriesInDistrict(String district, String name) {
        return townIndustryRepository.findSimilarTownIndustryByTownCodeAndQuarterAndNameInDistrict(district, name);
    }

    public MarketTrendsResponse getIndustriesBusinessPeriod(int code, int quarter) {
        MarketTrends marketTrends = marketTrendRepository.findMarketTrendsByTownCodeAndQuarter(code, quarter);
        return new MarketTrendsResponse(marketTrends.getOperateSaleAvg());
    }

    public MarketTrendsResponse getIndustriesBusinessPeriodInDistrict(String district, int quarter) {
        List<MarketTrends> marketTrendsList = marketTrendRepository.findMarketTrendsByTownCodeAndQuarterInDistrict(district, quarter);
        // average of operateSaleAvg
        Long operateSaleAvg = (long) marketTrendsList.stream()
                .mapToDouble(MarketTrends::getOperateSaleAvg)
                .average()
                .orElse(0);
        return new MarketTrendsResponse(operateSaleAvg);
    }

    public SalesResponse getIndustriesSales(int code, String name) {
        SalesVO salesVO = salesRepository.findByTownAndIndustry(code, name);
        return SalesResponse.of(salesVO, true);
    }

    public SalesResponse getIndustriesSalesInDistrict(String district, String name) {
        List<Sales> sales = salesRepository.findByTownAndIndustryInDistrict(district, name);
        return SalesResponse.of(new SalesVO(
                (long) sales.stream().mapToLong(Sales::getMondaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getTuesdaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getWednesdaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getThursdaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getFridaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getSaturdaySales).average().orElse(0),
                (long) sales.stream().mapToLong(Sales::getSundaySales).average().orElse(0)
        ), true);
    }

    @Async
    public MarketAnalysisResponse getTownSalesInfo(Long code, String industryName, String email) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            Town townCurr = townRepository.findByCodeAndQuarter(String.valueOf(code), 20241L).orElseThrow();
            Town townPast = townRepository.findByCodeAndQuarter(String.valueOf(code), 20241L).orElseThrow();

            Industry industry = industryRepository.findByName(industryName).orElseThrow();
            TownIndustry townIndustry = townIndustryRepository.findByTownAndIndustry(townCurr, industry);
            Sales sales = salesRepository.findByTownIndustry(townIndustry);
            SalesDiffVO salesDiffVO = townIndustryRepository.getTownSalesRawData(code, industryName);

            Population floatingPopulationCurr = populationRepository.findByTownAndPopulationType(townCurr, PopulationType.FloatingPopulation);
            Population floatingPopulationPast = populationRepository.findByTownAndPopulationType(townPast, PopulationType.FloatingPopulation);

            Population workingPopulationCurr = populationRepository.findByTownAndPopulationType(townCurr, PopulationType.WorkingPopulation);
            Population workingPopulationPast = populationRepository.findByTownAndPopulationType(townPast, PopulationType.WorkingPopulation);

            MarketAnalysisResponse marketAnalysisResponse = MarketAnalysisResponse.builder()
                    .townName(townCurr.getName())
                    .industryName(industryName)
                    .quarter(salesDiffVO.getQuarter())
                    .currentQuarterSales(formatNumber(salesDiffVO.getCurrentQuarterSales()))
                    .increaseRate(formatPercentage(salesDiffVO.getIncreaseRate()))
                    .maxDaySales(getMaxDaysSales(sales))
                    .maxTimesSales(getMaxTimesSales(sales))
                    .agePopulation(getMaxAgeSales(sales))
                    .floatingPopulationIncrease(Double.parseDouble(formatPercentage(getFloatingPopulationIncrease(floatingPopulationCurr, floatingPopulationPast))))
                    .workingPopulationIncrease(Double.parseDouble(formatPercentage(getFloatingPopulationIncrease(workingPopulationCurr, workingPopulationPast))))
                    .firstGender(getMaxGenderSales(sales).get(0))
                    .secondGender(getMaxGenderSales(sales).get(1))
                    .newStores(townIndustry.getOpenStoreCount().intValue())
                    .closedStores(townIndustry.getCloseStoreCount().intValue())
                    .maleVsFemaleSales(formatPercentage(Double.parseDouble(getMaxGenderSales(sales).get(2))))
                    .build();

            messageHelper.setSubject("가게뚝딱: " + marketAnalysisResponse.getTownName() + " 최신 분기 매출 분석");
            messageHelper.setTo(email);
            Context context = new Context();
            context.setVariable("response", marketAnalysisResponse);
            String html = templateEngine.process("mail.html", context);
            messageHelper.setText(html, true);
            javaMailSender.send(message);

            return marketAnalysisResponse;
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
        }

        throw new IllegalArgumentException("Failed to send email");
    }
    
    private String getMaxDaysSales(Sales sales) {
        long maxDays = Math.max(
                Math.max(
                        Math.max(
                                Math.max(
                                        Math.max(sales.getMondaySales(), sales.getTuesdaySales()),
                                        Math.max(sales.getWednesdaySales(), sales.getThursdaySales())
                                ),
                                sales.getFridaySales()
                        ),
                        sales.getSaturdaySales()
                ),
                sales.getSundaySales()
        );

        if (maxDays == sales.getMondaySales()) {
            return "월요일";
        } else if (maxDays == sales.getTuesdaySales()) {
            return "화요일";
        } else if (maxDays == sales.getWednesdaySales()) {
            return "수요일";
        } else if (maxDays == sales.getThursdaySales()) {
            return "목요일";
        } else if (maxDays == sales.getFridaySales()) {
            return "금요일";
        } else if (maxDays == sales.getSaturdaySales()) {
            return "토요일";
        } else if (maxDays == sales.getSundaySales()) {
            return "일요일";
        }

        throw new IllegalArgumentException("Invalid sales data");
    }

    private String getMaxAgeSales(Sales sales) {
        long maxTime =
                Math.max(
                        Math.max(
                                Math.max(
                                        Math.max(sales.getAge10sSales(), sales.getAge20sSales()),
                                        Math.max(sales.getAge30sSales(), sales.getAge40sSales())
                                ),
                                sales.getAge50sSales()
                        ),
                        sales.getAge60sAndMoreSales()
                );

        if (maxTime == sales.getAge10sSales()) {
            return "10대";
        } else if (maxTime == sales.getAge20sSales()) {
            return "20대";
        } else if (maxTime == sales.getAge30sSales()) {
            return "30대";
        } else if (maxTime == sales.getAge40sSales()) {
            return "40대";
        } else if (maxTime == sales.getAge50sSales()) {
            return "50대";
        } else {
            return "60대 이상";
        }
    }

    private double getFloatingPopulationIncrease(Population populationCurr, Population populationPast) {
        return (double) (populationCurr.getTotalPopulation() - populationPast.getTotalPopulation()) / populationPast.getTotalPopulation() * 100;
    }

    private String getMaxTimesSales(Sales sales) {
        long maxAge =
                Math.max(
                        Math.max(
                                Math.max(
                                        Math.max(sales.getHour_0_6(), sales.getHour_6_11()),
                                        Math.max(sales.getHour_11_14(), sales.getHour_14_17())
                                ),
                                sales.getHour_17_21()
                        ),
                        sales.getHour_21_24()
                );

        if (maxAge == sales.getHour_0_6()) {
            return "0시 ~ 6시";
        } else if (maxAge == sales.getHour_6_11()) {
            return "6시 ~ 11시";
        } else if (maxAge == sales.getHour_11_14()) {
            return "11시 ~ 14시";
        } else if (maxAge == sales.getHour_14_17()) {
            return "14시 ~ 17시";
        } else if (maxAge == sales.getHour_17_21()) {
            return "17시 ~ 21시";
        } else {
            return "21시 ~ 24시";
        }
    }

    private List<String> getMaxGenderSales(Sales sales) {
        long maxGender = Math.max(sales.getMenSales(), sales.getWomenSales());


        if (maxGender == sales.getMenSales()) {
            double percentageDifference = (double) (sales.getMenSales() - sales.getWomenSales()) / sales.getWomenSales() * 100;
            return List.of("남성", "여성", String.valueOf(percentageDifference));
        } else {
            double percentageDifference = (double) (sales.getWomenSales() - sales.getMenSales()) / sales.getMenSales() * 100;
            return List.of("여성", "남성", String.valueOf(percentageDifference));
        }
    }

    private String formatNumber(long number) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        return formatter.format(number);
    }

    private String formatPercentage(double number) {
        return String.format("%.1f", number);
    }
}

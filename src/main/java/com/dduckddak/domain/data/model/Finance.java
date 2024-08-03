package com.dduckddak.domain.data.model;

import com.dduckddak.domain.town.model.Town;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long averageMonthlyIncome;      // 월 평균소득
    private Long totalExpenditure;          // 총 지출 금액
    private Long groceryExpenditure;        // 식료품 지출
    private Long clothingExpenditure;       // 의류 지출
    private Long householdGoodsExpenditure; // 생활용품 지출
    private Long medicalExpenditure;        // 의료비 지출
    private Long transportationExpenditure; // 교통 지출
    private Long educationExpenditure;      // 교육 지출
    private Long entertainmentExpenditure;  // 유흥 지출
    private Long leisureCultureExpenditure; // 여가 문화 지출
    private Long foodExpenditure;           // 음식 지출

    @OneToOne(fetch = FetchType.LAZY)
    private Town town;

    @Builder
    public Finance(Long averageMonthlyIncome, Long clothingExpenditure, Long educationExpenditure, Long entertainmentExpenditure, Long foodExpenditure, Long groceryExpenditure, Long householdGoodsExpenditure, Long id, Long leisureCultureExpenditure, Long medicalExpenditure, Long totalExpenditure, Town town, Long transportationExpenditure) {
        this.averageMonthlyIncome = averageMonthlyIncome;
        this.clothingExpenditure = clothingExpenditure;
        this.educationExpenditure = educationExpenditure;
        this.entertainmentExpenditure = entertainmentExpenditure;
        this.foodExpenditure = foodExpenditure;
        this.groceryExpenditure = groceryExpenditure;
        this.householdGoodsExpenditure = householdGoodsExpenditure;
        this.id = id;
        this.leisureCultureExpenditure = leisureCultureExpenditure;
        this.medicalExpenditure = medicalExpenditure;
        this.totalExpenditure = totalExpenditure;
        this.town = town;
        this.transportationExpenditure = transportationExpenditure;
    }
}

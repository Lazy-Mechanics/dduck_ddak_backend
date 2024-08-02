package com.dduckddak.domain.data.model;

import com.dduckddak.domain.town.model.TownId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Finance {
    @EmbeddedId
    private TownId townId;

    private Long averageMonthlyIncome;
    private Long totalExpenditure;
    private Long foodExpenditure;
    private Long medicalExpenditure;

}

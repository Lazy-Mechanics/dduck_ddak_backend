package com.dduckddak.domain.data.model;

import com.dduckddak.domain.town.model.Town;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketTrends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Town town;

    private String indexName; //지표명
    private Long operateAvg;
    private Long closeAvg;
    private Long operateAvgBySeoul;
    private Long closeAvgBySeoul;

    public MarketTrends(Long closeAvg, Long closeAvgBySeoul, Long id, String indexName, Long operateAvg, Long operateAvgBySeoul, Town town) {
        this.closeAvg = closeAvg;
        this.closeAvgBySeoul = closeAvgBySeoul;
        this.id = id;
        this.indexName = indexName;
        this.operateAvg = operateAvg;
        this.operateAvgBySeoul = operateAvgBySeoul;
        this.town = town;
    }
}

package com.dduckddak.domain.data.model;

import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.model.TownId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketTrends {

    @EmbeddedId
    private TownId townId;

    private String indexName; //지표명
    private Long operateAvg;
    private Long closeAvg;
    private Long operateAvgBySeoul;
    private Long closeAvgBySeoul;

    @Builder
    public MarketTrends(TownId townId, String indexName, Long operateAvg, Long closeAvg, Long operateAvgBySeoul, Long closeAvgBySeoul) {
        this.townId = townId;
        this.indexName = indexName;
        this.operateAvg = operateAvg;
        this.closeAvg = closeAvg;
        this.operateAvgBySeoul = operateAvgBySeoul;
        this.closeAvgBySeoul = closeAvgBySeoul;
    }

    public static MarketTrends of(TownId townId, String indexName, Long operateAvg, Long closeAvg, Long operateAvgBySeoul, Long closeAvgBySeoul) {
        return MarketTrends.builder()
                .townId(townId)
                .indexName(indexName)
                .operateAvg(operateAvg)
                .closeAvg(closeAvg)
                .operateAvgBySeoul(operateAvgBySeoul)
                .closeAvgBySeoul(closeAvgBySeoul)
                .build();
    }
}

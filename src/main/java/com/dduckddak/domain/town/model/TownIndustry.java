package com.dduckddak.domain.town.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class TownIndustry {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Town town;

    @ManyToOne(fetch = FetchType.LAZY)
    private Industry industry;

    private Long storeCount;
    private Long similarStoreCount;
    private Long openStoreCount;
    private Long closeStoreCount;

    @Builder
    public TownIndustry(long id, Industry industry, Town town, Long storeCount, Long similarStoreCount, Long openStoreCount, Long closeStoreCount) {
        this.id = id;
        this.industry = industry;
        this.town = town;
        this.storeCount = storeCount;
        this.similarStoreCount = similarStoreCount;
        this.openStoreCount = openStoreCount;
        this.closeStoreCount = closeStoreCount;
    }
}

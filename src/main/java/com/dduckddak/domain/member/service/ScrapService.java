package com.dduckddak.domain.member.service;

import com.dduckddak.domain.member.controller.dto.ScrapRequest;
import com.dduckddak.domain.member.controller.dto.ScrapResponse;
import com.dduckddak.domain.member.model.Scrap;
import com.dduckddak.domain.member.repository.ScrapRepository;
import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapService {
    private final TownRepository townRepository;
    private final ScrapRepository scrapRepository;

    @Transactional
    public void saveScrap(ScrapRequest scrapRequest) {
        Scrap scrap = new Scrap(scrapRequest);
        if (scrapRepository.existsByTownCodeAndIndustryNameAndQuarter(scrap.getTownCode(), scrap.getIndustryName(), scrap.getQuarter())) {
            throw new RuntimeException("이미 스크랩한 지역입니다.");
        }
        scrapRepository.save(scrap);
    }

    @Transactional
    public void deleteScrap(Long code) {
        Scrap scrap = scrapRepository.findByTownCode(code)
                .orElseThrow(() -> new RuntimeException("해당 지역이 존재하지 않습니다."));
        scrapRepository.delete(scrap);
    }

    public List<ScrapResponse> getScrapList() {
        return scrapRepository.findAll().stream()
                .map(scrap -> {
                    Town town = townRepository.findByCodeAndQuarter(String.valueOf(scrap.getTownCode()), scrap.getQuarter())
                            .orElseThrow(() -> new RuntimeException("해당 지역이 존재하지 않습니다."));
                    return new ScrapResponse(Long.parseLong(town.getCode()), town.getName(), scrap.getIndustryName(), scrap.getQuarter());
                })
                .toList();
    }
}

package com.dduckddak.domain.member.service;

import com.dduckddak.domain.member.controller.dto.ScrapRequest;
import com.dduckddak.domain.member.controller.dto.ScrapResponse;
import com.dduckddak.domain.member.model.Member;
import com.dduckddak.domain.member.model.Scrap;
import com.dduckddak.domain.member.repository.MemberRepository;
import com.dduckddak.domain.member.repository.ScrapRepository;
import com.dduckddak.domain.town.model.Town;
import com.dduckddak.domain.town.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ScrapService {
    private final TownRepository townRepository;
    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveScrap(ScrapRequest scrapRequest) {
        Member member = memberRepository.findByEmail(scrapRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));

        log.info("ScrapRequest: {}", scrapRequest);
        Scrap scrap = new Scrap(scrapRequest, member);
        if (scrapRepository.existsByTownCodeAndQuarter(scrap.getTownCode(), scrap.getQuarter())) {
            throw new RuntimeException("이미 스크랩한 지역입니다.");
        }

        scrapRepository.save(scrap);
    }

    @Transactional
    public void deleteScrap(Long code, String email) {
        Scrap scrap = scrapRepository.findByTownCode(code)
                .orElseThrow(() -> new RuntimeException("해당 지역이 존재하지 않습니다."));

        if (!scrap.getMember().getEmail().equals(email)) {
            throw new RuntimeException("해당 사용자의 스크랩이 아닙니다.");
        }

        scrapRepository.delete(scrap);
    }

    public List<ScrapResponse> getScrapList(String email) {
        return scrapRepository.findAll().stream()
                .map(scrap -> {
                    Town town = townRepository.findByCodeAndQuarter(String.valueOf(scrap.getTownCode()), scrap.getQuarter())
                            .orElseThrow(() -> new RuntimeException("해당 지역이 존재하지 않습니다."));
                    return new ScrapResponse(Long.parseLong(town.getCode()), town.getName(), scrap.getQuarter());
                })
                .toList();
    }
}

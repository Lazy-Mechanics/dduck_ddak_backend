package com.dduckddak.domain.data.member.controller;

import com.dduckddak.domain.data.member.controller.dto.ScrapRequest;
import com.dduckddak.domain.data.member.controller.dto.ScrapResponse;
import com.dduckddak.domain.data.member.service.ScrapService;
import com.dduckddak.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dduckddak.global.ApiResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scraps")
@Tag(name = "스크랩 조회", description = "스크랩 API")
public class ScrapController {
    private final ScrapService scrapService;

    @Operation(summary = "스크랩 저장", description = "parameter로 townCode(행정동코드), industryName(업종명), quarter(분기)를 받아 스크랩을 저장합니다.")
    @PostMapping
    public void saveScrap(@RequestBody ScrapRequest scrapRequest) {
        scrapService.saveScrap(scrapRequest);
    }

    @Operation(summary = "스크랩 삭제", description = "parameter로 townCode(행정동코드)를 받아 스크랩을 삭제합니다.")
    @DeleteMapping("/delete")
    public void deleteScrap(@RequestParam(value = "town-code") Long code,
                            @RequestParam(value = "email") String email) {
        scrapService.deleteScrap(code, email);
    }

    @Operation(summary = "스크랩 조회", description = "스크랩 목록을 조회합니다.")
    @GetMapping("/list")
    public ApiResponse<List<ScrapResponse>> getScrapList(@RequestParam(value = "email") String email) {
        return success(scrapService.getScrapList(email));
    }
}

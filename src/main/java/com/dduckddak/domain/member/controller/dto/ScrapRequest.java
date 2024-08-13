package com.dduckddak.domain.member.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScrapRequest {
    @JsonProperty("town-code")
    private Long townCode;
    @JsonProperty("industry-name")
    private String industryName;
    @JsonProperty("quarter")
    private Long quarter;
    private String email;
}

package com.dduckddak.domain.data.member.controller.dto;

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
    private Long townCode;
    @JsonProperty("quarter")
    private Long quarter;
    private String email;
}

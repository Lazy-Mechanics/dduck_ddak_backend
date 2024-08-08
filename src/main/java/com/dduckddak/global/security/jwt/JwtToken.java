package com.dduckddak.global.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtToken {

    private String tokenType;
    private String accessToken;
    private Long accessExpirationTime;

    @Builder
    private JwtToken(String tokenType, String accessToken, String refreshToken, Long accessExpirationTime, Long refreshExpirationTime) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.accessExpirationTime = accessExpirationTime;
    }
}

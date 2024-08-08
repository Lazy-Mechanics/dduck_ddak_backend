package com.dduckddak.global.security.jwt;


import com.dduckddak.global.exception.ErrorCode;
import com.dduckddak.global.exception.custom_exception.InvalidException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {


    @Value("${custom.jwt.token.access-expiration-time}")
    private long accessExpirationTime;


    private final Key key;

    @Autowired
    public JwtProvider(@Value("${custom.jwt.token.secret}") String secretKey, JwtUtils jwtUtils) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * @Description
     * 토큰 발급 및 재발급 시 동작
     */
    public JwtToken createJwtToken(String username, String authorities) {

        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("currentTimeMillis", System.currentTimeMillis());
        claims.put("roles", authorities);

        return JwtToken.builder()
                .accessToken(createToken(claims, new Date(now.getTime() + accessExpirationTime)))
                .tokenType("Bearer ")
                .accessExpirationTime(accessExpirationTime / 1000).build();
    }

    public String createToken(Claims claims, Date expiredDate){
        return  Jwts.builder()
                .setClaims(claims) // 아이디, 권한정보
                .setExpiration(expiredDate) // 만료기간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }



    public Authentication getAuthenticationByAccessToken(String accessToken){
        Claims claims = parseAccessTokenClaims(accessToken);

        if (claims.get("roles") == null){
            throw new InvalidException(ErrorCode.EMPTY_AUTHORITY);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Claims parseAccessTokenClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }
        catch (ExpiredJwtException e) {
            throw new InvalidException(ErrorCode.EXPIRED_PERIOD_ACCESS_TOKEN);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }



    /**
     * @Description
     * 토큰의 만료여부와 유효성에 대해 검증합니다.
     */
    public boolean validateAccessToken(String accessToken) {
        try {
            parseToken(accessToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new InvalidException(ErrorCode.EXPIRED_PERIOD_ACCESS_TOKEN);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    private Jws<Claims> parseToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}

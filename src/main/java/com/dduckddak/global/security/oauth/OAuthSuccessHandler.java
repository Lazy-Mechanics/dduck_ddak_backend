package com.dduckddak.global.security.oauth;


import com.dduckddak.domain.data.member.model.Member;
import com.dduckddak.domain.data.member.model.MemberRole;
import com.dduckddak.domain.data.member.repository.MemberRepository;
import com.dduckddak.global.security.jwt.JwtProvider;
import com.dduckddak.global.security.jwt.JwtToken;
import com.dduckddak.global.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");
        String provider = oAuth2User.getAttribute("provider");

        Optional<Member> findMember = memberRepository.findByEmail(email + "_" + provider);

        // 회원이 아닌 경우에 회원 가입 진행
        Member member = null;
        if (findMember.isEmpty()) {
            member = Member.builder()
                    .email(email + "_" + provider)
                    .nickname(name)
                    .memberRole(MemberRole.MEMBER)
                    .profileImgUrl(picture)
                    .build();

            memberRepository.save(member);
        } else {
            member = findMember.get();
        }

        // OAuth2User 객체에서 권한 가져옴
        JwtToken jwtToken = jwtProvider.createJwtToken(member.getEmail(), member.getRole().getValue());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(200);
//        response.addHeader("Authorization", jwtToken.getAccessToken());
//        response.getWriter().write(
//                objectMapper.writeValueAsString(jwtToken)
//        );

        String randomCookieValue = UUID.randomUUID().toString();
        ResponseCookie cookie = ResponseCookie.from("accessToken", randomCookieValue)
                .path("/api/items/")
                .sameSite("Strict")
                .domain("gadduck.info")
                .httpOnly(true)
                .secure(true)
                .maxAge(48200)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        response.sendRedirect("https://www.gadduck.info");
    }
}

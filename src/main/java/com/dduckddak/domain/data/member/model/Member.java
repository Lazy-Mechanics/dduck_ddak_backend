package com.dduckddak.domain.data.member.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
    private String profileImgUrl;

    @Builder
    public Member(String nickname, String email, MemberRole memberRole, String profileImgUrl) {
        this.nickname = nickname;
        this.email = email;
        this.role = memberRole;
        this.profileImgUrl = profileImgUrl;
    }

    public static Member of(String nickname, String email,MemberRole memberRole, String profileImgUrl){
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .memberRole(memberRole)
                .profileImgUrl(profileImgUrl)
                .build();
    }
}

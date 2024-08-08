package com.dduckddak.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");


    private final String value;

    public static MemberRole checkMemberRole(String role) {
        return switch (role) {
            case "ADMIN" -> ADMIN;
            case "MEMBER" -> MEMBER;
            default -> null;
        };
    }
}
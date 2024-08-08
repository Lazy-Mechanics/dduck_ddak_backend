package com.dduckddak.global.security.jwt;


import com.dduckddak.global.exception.ErrorCode;
import com.dduckddak.global.exception.custom_exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    public static String extractBearerToken(String token) {
        if (token != null) {
            if (!token.startsWith("Bearer")) {
                throw new BadRequestException(ErrorCode.INVALID_TYPE_TOKEN);
            }

            return token.split(" ")[1].trim();
        }
        return null;
    }
}

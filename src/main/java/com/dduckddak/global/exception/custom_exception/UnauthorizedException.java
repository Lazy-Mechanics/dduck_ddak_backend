package com.dduckddak.global.exception.custom_exception;


import com.dduckddak.global.exception.CustomException;
import com.dduckddak.global.exception.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

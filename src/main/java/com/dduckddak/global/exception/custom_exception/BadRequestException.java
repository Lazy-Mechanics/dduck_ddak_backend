package com.dduckddak.global.exception.custom_exception;


import com.dduckddak.global.exception.CustomException;
import com.dduckddak.global.exception.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
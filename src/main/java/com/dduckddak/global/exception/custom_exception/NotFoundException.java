package com.dduckddak.global.exception.custom_exception;


import com.dduckddak.global.exception.CustomException;
import com.dduckddak.global.exception.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

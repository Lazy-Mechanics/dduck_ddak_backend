package com.dduckddak.global.exception.custom_exception;


import com.dduckddak.global.exception.CustomException;
import com.dduckddak.global.exception.ErrorCode;

public class InvalidException extends CustomException {
    public InvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}

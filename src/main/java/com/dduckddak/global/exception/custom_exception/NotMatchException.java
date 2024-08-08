package com.dduckddak.global.exception.custom_exception;


import com.dduckddak.global.exception.CustomException;
import com.dduckddak.global.exception.ErrorCode;

public class NotMatchException extends CustomException {
    public NotMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}

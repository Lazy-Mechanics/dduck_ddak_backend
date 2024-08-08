package com.dduckddak.global.exception.custom_exception;


import com.dduckddak.global.exception.CustomException;
import com.dduckddak.global.exception.ErrorCode;

public class S3Exception extends CustomException {
    public S3Exception(ErrorCode errorCode) {
        super(errorCode);
    }
}
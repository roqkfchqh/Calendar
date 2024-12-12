package com.calendar.exception;

public class CustomException extends BaseException {
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getStatus());
    }
}
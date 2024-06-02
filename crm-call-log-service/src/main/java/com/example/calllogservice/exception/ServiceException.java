package com.example.calllogservice.exception;

import lombok.Getter;

import static com.example.calllogservice.exception.ErrorCode.GENERIC_ERROR;

@Getter
public class ServiceException extends RuntimeException {

    private ErrorCode errorCode;
    private Object[] args;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, Object[] args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }

    public ServiceException(String message, Throwable cause) {
        super(GENERIC_ERROR.getMessage(), cause);
        this.errorCode = GENERIC_ERROR;
        this.args = new String[]{message};
    }
}

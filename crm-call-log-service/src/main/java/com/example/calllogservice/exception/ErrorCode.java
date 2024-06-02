package com.example.calllogservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    GENERIC_ERROR("ERROR-CLS-0000", "exception.general.error", HttpStatus.BAD_REQUEST),
    INVALID_INPUT("ERROR-CLS-0001", "exception.invalid.input", HttpStatus.BAD_REQUEST),
    CALL_LOG_NOT_FOUND("ERROR-CLS-0002", "exception.calllog.notfound", HttpStatus.NOT_FOUND),
    CUSTOMER_ID_NOT_FOUND("ERROR-CLS-0003", "exception.customerID.notfound", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

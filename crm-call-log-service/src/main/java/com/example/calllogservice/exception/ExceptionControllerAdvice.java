package com.example.calllogservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
class ExceptionControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(ServiceException.class)
    ResponseEntity<ErrorResponse> handleServiceException(final ServiceException e,
                                                         final HttpServletRequest request,
                                                         final HttpServletResponse response) {
        log.error("Service Exception with error", e);
        final String message = messageSource.getMessage(e.getErrorCode().getMessage(), e.getArgs(), Locale.getDefault());
        final ErrorResponse errorResponse = ErrorResponse.builder().code(e.getErrorCode().getCode()).message(message).build();
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ErrorResponse> handleTypeMismatchException(final MethodArgumentTypeMismatchException e,
                                                              final HttpServletRequest request,
                                                              final HttpServletResponse response) {
        log.error("Validation mismatch error on input", e);
        final String message = messageSource.getMessage(ErrorCode.INVALID_INPUT.getMessage(),
                new Object[]{e.getRequiredType().getSimpleName()}, Locale.getDefault());
        final ErrorResponse errorResponse = ErrorResponse.builder().code(ErrorCode.INVALID_INPUT.getCode()).message(message).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity handleGeneralException(final RuntimeException e,
                                          final HttpServletRequest request,
                                          final HttpServletResponse response) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

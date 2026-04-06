package com.example.project1.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {
    private final int status;
    private final String message;

    public static ExceptionResponse of(ErrorCode errorCode){
        return ExceptionResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }
}

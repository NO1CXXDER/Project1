package com.example.project1.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCoded){
        super(errorCoded.getMessage());
        this.errorCode = errorCoded;
    }
}

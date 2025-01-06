package com.bloggingapp.exception;

import lombok.Data;

@Data
public class CustomServiceException extends RuntimeException {
    private String errorMessage;

    public CustomServiceException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

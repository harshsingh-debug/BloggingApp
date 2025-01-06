package com.bloggingapp.exception;

import lombok.Data;

@Data
public class DataNotFoundException extends RuntimeException {
    private Integer userId;
    private String errorMessage;

    public DataNotFoundException(Integer userId, String errorMessage) {
        this.userId = userId;
        this.errorMessage = errorMessage;
    }
}

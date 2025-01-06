package com.bloggingapp.exceptionHandler;

import com.bloggingapp.exception.CustomServiceException;
import com.bloggingapp.exception.DataNotFoundException;
import com.bloggingapp.model.response.ExceptionServiceResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({CustomServiceException.class})
    public ResponseEntity<ExceptionServiceResponse> customServiceExceptionHandler(CustomServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionServiceResponse(e.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ExceptionServiceResponse> userNotFoundExceptionHandler(DataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionServiceResponse(e.getErrorMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionServiceResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionServiceResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName, message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionServiceResponse> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionServiceResponse("An unexpected error occurred. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

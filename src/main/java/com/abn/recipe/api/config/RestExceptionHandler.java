package com.abn.recipe.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abn.recipe.domain.exception.NotFoundException;

import lombok.Builder;
import lombok.NonNull;

@RestControllerAdvice
public class RestExceptionHandler {
    
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    ExceptionMessage handleException(final IllegalArgumentException exception) {
        return ExceptionMessage
            .builder()
            .code(HttpStatus.BAD_REQUEST.toString())
            .message(exception.getLocalizedMessage())
            .build();
    }
    
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ExceptionMessage handleException(final NotFoundException exception) {
        return ExceptionMessage
            .builder()
            .code(HttpStatus.NOT_FOUND.toString())
            .message(exception.getLocalizedMessage())
            .build();
    }
    
    @Builder
    public record ExceptionMessage(@NonNull String code, @NonNull String message) {
    }
}

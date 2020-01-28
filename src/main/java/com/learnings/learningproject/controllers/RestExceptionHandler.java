package com.learnings.learningproject.controllers;

import com.learnings.learningproject.models.exceptions.ApiError;
import com.learnings.learningproject.models.exceptions.ApiValidationError;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Logger;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Request validation failed.", ex);
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            FieldError fieldError = (FieldError) objectError;
            error.getSubErrors().add(new ApiValidationError(fieldError.getObjectName().replace("Dto", ""), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        });
        return this.buildResponseEntity(error);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Malformed specified JSON.", ex);
        return this.buildResponseEntity(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex)
    {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Entity not found.", ex);
        return this.buildResponseEntity(error);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError error)
    {
        return new ResponseEntity<>(error, error.getStatusError());
    }

}

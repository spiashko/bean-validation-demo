package com.siarhei.eventmanagement.errorhandlers;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@CrossOrigin
@ControllerAdvice
public class MyRestErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected final ResponseEntity<ValidationErrorResponse> handleConstraintValidationException(
      final ConstraintViolationException e) {
    final ValidationErrorResponse error = new ValidationErrorResponse();
    for (ConstraintViolation violation : e.getConstraintViolations()) {
      error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
    }
    return ResponseEntity.badRequest().body(error);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    return handleExceptionInternal(ex, message(ex), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      error.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return ResponseEntity.badRequest().body(error);
  }

  private ApiError message(final Exception ex) {
    final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
    final String devMessage = ex.getClass().getSimpleName();

    return ApiError.builder()
        .message(message)
        .developerMessage(devMessage)
        .build();
  }

}

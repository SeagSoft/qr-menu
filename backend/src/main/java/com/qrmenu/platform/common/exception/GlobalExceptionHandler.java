package com.qrmenu.platform.common.exception;

import com.qrmenu.platform.common.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Global exception handler for consistent API error responses.
 * Does not reveal stack traces or internal database information to clients.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessRule(BusinessRuleException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ApiErrorResponse.ValidationError> validationErrors = ex.getBindingResult().getFieldErrors().stream().map(this::toValidationError).toList();
        String traceId = UUID.randomUUID().toString();
        ApiErrorResponse response = ApiErrorResponse.builder().timestamp(Instant.now()).status(HttpStatus.BAD_REQUEST.value()).errorCode("VALIDATION_ERROR").message("Validation failed").path(request.getRequestURI()).validationErrors(validationErrors).traceId(traceId).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
        String traceId = UUID.randomUUID().toString();
        log.error("Unhandled exception [traceId={}]: {}", traceId, ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "An unexpected error occurred", request, traceId);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String errorCode, String message, HttpServletRequest request) {
        return buildResponse(status, errorCode, message, request, UUID.randomUUID().toString());
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String errorCode, String message, HttpServletRequest request, String traceId) {
        ApiErrorResponse response = ApiErrorResponse.builder().timestamp(Instant.now()).status(status.value()).errorCode(errorCode).message(message).path(request.getRequestURI()).traceId(traceId).build();
        return ResponseEntity.status(status).body(response);
    }

    private ApiErrorResponse.ValidationError toValidationError(FieldError fieldError) {
        return ApiErrorResponse.ValidationError.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).build();
    }
}

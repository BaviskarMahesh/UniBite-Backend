package com.unibite.unibit_backend.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    /// 404 not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException exception, HttpServletRequest request
            ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .message(exception.getMessage())
                        .status(404)
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build());
    }

    /// 400 bad request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(
            BadRequestException exception,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .message(exception.getMessage())
                        .status(400)
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build());
    }

    /// 401 - Unauthorized access
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(
            AccessDeniedException exception,HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiError.builder()
                        .message(exception.getMessage())
                        .status(402)
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build());
    }

    /// 500 Internal Server Error
    public ResponseEntity<ApiError> handleGeneral(
            Exception exception, HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.builder()
                        .message(exception.getMessage())
                        .status(500)
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build());
    }

    /// handle validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException exception,HttpServletRequest request
    ){
        String message=exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error->error.getDefaultMessage())
                .orElse("Validation Error");

        return ResponseEntity.badRequest().body(
                ApiError.builder()
                        .message(message)
                        .status(400)
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build()

        );

    }
}
